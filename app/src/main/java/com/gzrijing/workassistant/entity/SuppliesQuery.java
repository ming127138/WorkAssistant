package com.gzrijing.workassistant.entity;

public class SuppliesQuery {
    private String id;
    private String name;
    private String spec;
    private String unit;

    public SuppliesQuery() {
    }

    public SuppliesQuery(String id, String name, String spec, String unit) {
        this.id = id;
        this.name = name;
        this.spec = spec;
        this.unit = unit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
