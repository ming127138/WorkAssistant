package com.gzrijing.workassistant.db;

import org.litepal.crud.DataSupport;

public class MachineData extends DataSupport {
    private int id;
    private String applyId;             //机械申请单号
    private String returnId;            //机械退回单号
    private String No;                  //机械编号
    private String name;                //机械名称
    private String unit;                //机械单位
    private String num;                 //数量
    private String receivedState;       //领用状态（已安排，已领出，已退回）
    private BusinessData businessData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getReturnId() {
        return returnId;
    }

    public void setReturnId(String returnId) {
        this.returnId = returnId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getReceivedState() {
        return receivedState;
    }

    public void setReceivedState(String receivedState) {
        this.receivedState = receivedState;
    }

    public BusinessData getBusinessData() {
        return businessData;
    }

    public void setBusinessData(BusinessData businessData) {
        this.businessData = businessData;
    }
}
