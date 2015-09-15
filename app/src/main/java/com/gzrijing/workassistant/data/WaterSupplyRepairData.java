package com.gzrijing.workassistant.data;

import org.litepal.crud.DataSupport;

public class WaterSupplyRepairData extends DataSupport{
    private int id;
    private String time;            //报事时间
    private String address;         //工程地点
    private String reason;          //故障原因
    private String contacts;        //联系人
    private String tel;             //联系人电话
    private String rePairType;      //维修类型
    private String remarks;         //备注
    private String executor;        //施工员

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRePairType() {
        return rePairType;
    }

    public void setRePairType(String rePairType) {
        this.rePairType = rePairType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }
}
