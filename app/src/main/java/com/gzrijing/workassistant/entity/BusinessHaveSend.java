package com.gzrijing.workassistant.entity;

public class BusinessHaveSend {
    private String orderId;
    private String content;
    private String state;
    private String deadline;
    private String executors;

    public BusinessHaveSend() {
    }

    public BusinessHaveSend(String orderId, String content, String state, String deadline, String executors) {
        this.orderId = orderId;
        this.content = content;
        this.state = state;
        this.deadline = deadline;
        this.executors = executors;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getExecutors() {
        return executors;
    }

    public void setExecutors(String executors) {
        this.executors = executors;
    }
}
