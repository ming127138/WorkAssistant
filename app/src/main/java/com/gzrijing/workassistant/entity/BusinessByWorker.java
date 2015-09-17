package com.gzrijing.workassistant.entity;


public class BusinessByWorker {
    private String orderId;                 //工程编码
    private String type;                    //工程类型
    private String state;                   //工程状态
    private String deadline;                //工程期限
    private boolean urgent;                 //工程是否紧急
    private boolean temInfo;                //是否有临时信息
    private String flag;

    public BusinessByWorker() {
    }

    public BusinessByWorker(String orderId, String type, String state, String deadline, boolean urgent, boolean temInfo, String flag) {
        this.orderId = orderId;
        this.type = type;
        this.state = state;
        this.deadline = deadline;
        this.urgent = urgent;
        this.temInfo = temInfo;
        this.flag = flag;
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

    public boolean isTemInfo() {
        return temInfo;
    }

    public void setTemInfo(boolean temInfo) {
        this.temInfo = temInfo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
