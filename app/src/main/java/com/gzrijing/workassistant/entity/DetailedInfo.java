package com.gzrijing.workassistant.entity;

public class DetailedInfo {
    private String key;
    private String value;

    public DetailedInfo() {
    }

    public DetailedInfo(String key, String value) {
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
