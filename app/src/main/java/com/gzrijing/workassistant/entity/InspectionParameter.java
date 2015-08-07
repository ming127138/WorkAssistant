package com.gzrijing.workassistant.entity;

/**
 * Created by ME on 2015/8/4.
 */
public class InspectionParameter {
    private String key;
    private String value;

    public InspectionParameter() {
    }

    public InspectionParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
