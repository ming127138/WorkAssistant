package com.gzrijing.workassistant.entity;

public class InspectionFault {
    private String fault;
    private boolean isCheck;

    public InspectionFault() {
    }

    public InspectionFault(String fault, boolean isCheck) {
        this.fault = fault;
        this.isCheck = isCheck;
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
