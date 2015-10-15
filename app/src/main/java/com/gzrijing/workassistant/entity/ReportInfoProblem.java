package com.gzrijing.workassistant.entity;

public class ReportInfoProblem {
    private String content;
    private boolean flag;       //true:已解决，false:未解决

    public ReportInfoProblem() {
    }

    public ReportInfoProblem(String content, boolean flag) {
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
