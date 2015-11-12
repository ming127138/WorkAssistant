package com.gzrijing.workassistant.entity;


import java.util.ArrayList;
import java.util.List;

public class BusinessByLeader {
    private String orderId;                 //工程编码
    private String type;                    //工程类型
    private String state;                   //工程状态
    private String deadline;                //工程期限
    private boolean urgent;                 //工程是否紧急
    private String flag;
    private List<DetailedInfo> detailedInfos = new ArrayList<DetailedInfo>();   //详细信息

    public BusinessByLeader() {
    }

    public BusinessByLeader(String orderId, String type, String state, String deadline, boolean urgent, String flag, List<DetailedInfo> detailedInfos) {
        this.orderId = orderId;
        this.type = type;
        this.state = state;
        this.deadline = deadline;
        this.urgent = urgent;
        this.flag = flag;
        this.detailedInfos = detailedInfos;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<DetailedInfo> getDetailedInfos() {
        return detailedInfos;
    }

    public void setDetailedInfos(List<DetailedInfo> detailedInfos) {
        this.detailedInfos = detailedInfos;
    }
}
