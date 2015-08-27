package com.gzrijing.workassistant.data;


import org.litepal.crud.DataSupport;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;


public class PipeRepairOrderData extends DataSupport {
    private int id;
    private String orderId;
    private boolean urgent;
    private String info1;
    private String info2;
    private String info3;
    private String deadline;
    private boolean receive;
    private String remindTime;
    private boolean state;
    private List<SuppliesData> suppliesDataList = new ArrayList<SuppliesData>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isReceive() {
        return receive;
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<SuppliesData> getSuppliesDataList() {
        return DataSupport.where("PipeRepairOrderData_id = ?", String.valueOf(id)).find(SuppliesData.class);
    }

    public void setSuppliesDataList(List<SuppliesData> suppliesDataList) {
        this.suppliesDataList = suppliesDataList;
    }
}
