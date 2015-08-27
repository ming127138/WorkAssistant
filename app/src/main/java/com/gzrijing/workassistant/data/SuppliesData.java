package com.gzrijing.workassistant.data;

import org.litepal.crud.DataSupport;

public class SuppliesData extends DataSupport {
    private int id;
    private String No;
    private String name;
    private String spec;
    private String unit;
    private String unitPrice;
    private int num;
    private PipeRepairOrderData pipeRepairOrderData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        No = no;
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

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public PipeRepairOrderData getPipeRepairOrderData() {
        return pipeRepairOrderData;
    }

    public void setPipeRepairOrderData(PipeRepairOrderData pipeRepairOrderData) {
        this.pipeRepairOrderData = pipeRepairOrderData;
    }
}
