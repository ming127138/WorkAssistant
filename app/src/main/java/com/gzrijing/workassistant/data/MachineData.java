package com.gzrijing.workassistant.data;

import org.litepal.crud.DataSupport;

public class MachineData extends DataSupport {
    private int id;
    private String No;                  //机械编号
    private String applyId;             //机械申请单号
    private String name;                //机械名称
    private String spec;                //机械规格
    private String unit;                //机械单位
    private int num;                    //数量
    private String state;               //申请机械状态(申请中，未能准备，领用，退回申请中，退回)
    private String returnType;          //机械退回类型（正常，故障）
    private BusinessData businessData;

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

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public BusinessData getBusinessData() {
        return businessData;
    }

    public void setBusinessData(BusinessData businessData) {
        this.businessData = businessData;
    }
}
