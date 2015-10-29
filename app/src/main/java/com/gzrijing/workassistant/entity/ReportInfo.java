package com.gzrijing.workassistant.entity;

public class ReportInfo {
    private String content;
    private boolean flag;           //true:已审批，false:未审批

    public ReportInfo() {
    }

    public ReportInfo(String content, boolean flag) {
        this.content = content;
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
