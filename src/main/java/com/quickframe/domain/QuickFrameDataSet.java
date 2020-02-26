package com.quickframe.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuickFrameDataSet {

    @JsonProperty(value = "Object Number")
    private String objectNumber;

    @JsonProperty(value = "Classification")
    private String classification;

    public QuickFrameDataSet() {}

    public QuickFrameDataSet(String objectNumber, String classification) {
        this.objectNumber = objectNumber;
        this.classification = classification;
    }

    public String getObjectNumber() {
        return objectNumber;
    }

    public void setObjectNumber(String objectNumber) {
        this.objectNumber = objectNumber;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Override
    public String toString() {
        return "QuickFrameDataSet{" +
                "objectNumber='" + objectNumber + '\'' +
                ", classification='" + classification + '\'' +
                '}';
    }
}
