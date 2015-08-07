package com.gzrijing.workassistant.entity;

/**
 * Created by ME on 2015/8/4.
 */
public class InspectionStandard {
    private String standard;
    private boolean isCheck;

    public InspectionStandard() {
    }

    public InspectionStandard(String standard, boolean isCheck) {
        this.standard = standard;
        this.isCheck = isCheck;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
