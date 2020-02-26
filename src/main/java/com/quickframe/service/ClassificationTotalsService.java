package com.quickframe.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.quickframe.domain.ClassificationTotals;
import com.quickframe.domain.QuickFrameDataSet;
import com.quickframe.repository.ClassificationTotalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassificationTotalsService {
    @Autowired
    private ClassificationTotalsRepository classificationTotalsRepository;

    private static Map<String, Integer> ctMap = new HashMap<>();

    StringBuilder objectNumberSB;

    public void saveAllClassificationTotals(List<ClassificationTotals> classificationTotals) {
        classificationTotalsRepository.saveAll(classificationTotals);
    }

    // Problem 1: Find records which have a valid "Object Number" value
    public void parseCSVFile(File csvFile) throws IOException {

        // ignore the columns in csv file that aren't being used in the QuickFrameDataSet entity
        ObjectMapper csvMapper = new CsvMapper().enable(CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE);

        // use first row as the header
        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        MappingIterator<QuickFrameDataSet> it = csvMapper.readerFor(QuickFrameDataSet.class)
                .with(schema)
                .readValues(csvFile);
        while (it.hasNext()) {
            QuickFrameDataSet qfd = it.next();

            String objectNumber = qfd.getObjectNumber();

            // check if "Object Number" is empty
            if (objectNumber.isEmpty()) {
                continue;
            }

            // check if "Object Number" starts and ends with a digit
            if (!Character.isDigit(objectNumber.charAt(0)) || !Character.isDigit(objectNumber.charAt(objectNumber.length()-1))) {
                continue;
            }

            objectNumberSB = new StringBuilder(objectNumber);

            int numberOfDots = 0;
            for (int i = 0; i < objectNumberSB.length(); i++) {
                if (objectNumberSB.charAt(i) == '.') {
                    objectNumberSB.deleteCharAt(i);
                    numberOfDots++;
                }
            }

            if (numberOfDots != 2) { // if there are more or less than 2 dots, then ignore that row
                continue;
            } else {
                // check if this "Object Number" without any dots is, in fact, an integer
                try {
                    Integer num = Integer.parseInt(objectNumberSB.toString());
                } catch (NumberFormatException e) {
                    continue; // if something went wrong with turning it into an integer, ignore that row
                }

                // any row to come this far(to below code) has a valid "Object Number"

                // check if this record's "Classification" value is empty or is just whitespace
                if (!qfd.getClassification().trim().isEmpty()) {
                    aggregateClassificationTotal(qfd.getClassification());
                }

            }

        }
        storeAllClassificationTotals(ctMap);
    }

    // Problem 3: Aggregate the classification values

    public void aggregateClassificationTotal(String classification) {

        // Lowercase the classification value and capitalize the first letter; makes it easier for comparison later
        String classificationName = classification.substring(0, 1).toUpperCase() + classification.substring(1).toLowerCase();

        // if the classification is already in the map, add 1 to its "Totals" value
        // if its new, then add this classification with its total value as 1.
        if (ctMap.containsKey(classificationName)) {
            ctMap.put(classificationName, ctMap.get(classificationName) + 1);
        } else {
            ctMap.put(classificationName, 1);
        }
    }

    // Final step: Store the aggregated classification values in the database
    public void storeAllClassificationTotals(Map<String, Integer> classificationTotalsMap) {
        List<ClassificationTotals> ct_list = new ArrayList<>();
        ClassificationTotals ct = new ClassificationTotals();

        classificationTotalsMap.forEach((k, v) -> {
            ct.setClassification(k);
            ct.setTotals(v);
            ct_list.add(ct);
        });

        // store the list of distinct classification values with their counts in DB
        saveAllClassificationTotals(ct_list);
    }

}
