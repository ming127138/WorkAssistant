package com.gzrijing.workassistant.entity;

public class SuppliesVerifyInfo {
    private String name;
    private String spec;
    private String unit;
    private int num;
    private boolean isCheck;

    public SuppliesVerifyInfo() {
    }

    public SuppliesVerifyInfo(String name, String spec, String unit, int num, boolean isCheck) {
        this.name = name;
        this.spec = spec;
        this.unit = unit;
        this.num = num;
        this.isCheck = isCheck;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
