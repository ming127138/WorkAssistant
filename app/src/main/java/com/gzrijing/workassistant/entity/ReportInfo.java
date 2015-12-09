package com.gzrijing.workassistant.entity;

public class ReportInfo {
    private String id;
    private String fileNo;
    private String content;     //汇报内容
    private String picNum;      //图片数量
    private String reportor;    //汇报人
    private String reportTime;  //汇报时间
    private String state;       //问题是否解决 0为未解决，1为解决

    public ReportInfo() {
    }

    public ReportInfo(String id, String fileNo, String content, String picNum, String reportor, String reportTime, String state) {
        this.id = id;
        this.fileNo = fileNo;
        this.content = content;
        this.picNum = picNum;
        this.reportor = reportor;
        this.reportTime = reportTime;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileNo() {
        return fileNo;
    }

    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicNum() {
        return picNum;
    }

    public void setPicNum(String picNum) {
        this.picNum = picNum;
    }

    public String getReportor() {
        return reportor;
    }

    public void setReportor(String reportor) {
        this.reportor = reportor;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
