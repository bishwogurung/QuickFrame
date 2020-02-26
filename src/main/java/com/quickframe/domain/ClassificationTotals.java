package com.quickframe.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classification_totals")
public class ClassificationTotals {

    @Id
    @Column(name = "classification")
    private String classification;

    @Column(name = "totals")
    private Integer totals;

    public ClassificationTotals() {
    }

    public ClassificationTotals(String classification, Integer totals) {
        this.classification = classification;
        this.totals = totals;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    @Override
    public String toString() {
        return "ClassificationTotals{" +
                "classification='" + classification + '\'' +
                ", totals=" + totals +
                '}';
    }
}
