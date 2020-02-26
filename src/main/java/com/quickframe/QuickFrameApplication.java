package com.quickframe;

import com.quickframe.service.ClassificationTotalsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Map;

@SpringBootApplication
public class QuickFrameApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(QuickFrameApplication.class);

    @Autowired
    private ClassificationTotalsService classificationTotalsService;

    // this value can be different, depending on where you placed the file.
    // in this case, the csv file is one level above this project folder.
    private static String filePath = "../";

    private static String fileName = "DataEngineerDataSet.csv";
    private static File csvFile;


    public static void main(String[] args) {
        SpringApplication.run(QuickFrameApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            csvFile = new File(filePath + fileName);
        } catch (Exception e) {
            logger.error("Error: Something went wrong when trying to load the file: " + fileName, e);
        }

        classificationTotalsService.parseCSVFile(csvFile);
    }
}
