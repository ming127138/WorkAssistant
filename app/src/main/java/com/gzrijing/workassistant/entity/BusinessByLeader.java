package com.gzrijing.workassistant.entity;


import java.util.ArrayList;
import java.util.List;

public class BusinessByLeader {
    private String orderId;                 //工程编码
    private String type;                    //工程类型
    private String state;                   //工程状态
    private String deadline;                //工程期限
    private boolean urgent;                 //工程是否紧急
    private int machineApplyNum;            //标记有多少条未查看的机械申请单信息
    private int suppliesApplyNum;           //标记有多少条未查看的材料申请单信息
    private int temInfoNum;                 //标记有多少条未查看的临时信息
    private String flag;
    private List<DetailedInfo> detailedInfos = new ArrayList<DetailedInfo>();   //详细信息
    private List<PicUrl> picUrls = new ArrayList<PicUrl>();     //图片URL

    public BusinessByLeader() {
    }

    public BusinessByLeader(String orderId, String type, String state, String deadline, boolean urgent, int machineApplyNum, int suppliesApplyNum, int temInfoNum, String flag, List<DetailedInfo> detailedInfos, List<PicUrl> picUrls) {
        this.orderId = orderId;
        this.type = type;
        this.state = state;
        this.deadline = deadline;
        this.urgent = urgent;
        this.machineApplyNum = machineApplyNum;
        this.suppliesApplyNum = suppliesApplyNum;
        this.temInfoNum = temInfoNum;
        this.flag = flag;
        this.detailedInfos = detailedInfos;
        this.picUrls = picUrls;
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

    public int getMachineApplyNum() {
        return machineApplyNum;
    }

    public void setMachineApplyNum(int machineApplyNum) {
        this.machineApplyNum = machineApplyNum;
    }

    public int getSuppliesApplyNum() {
        return suppliesApplyNum;
    }

    public void setSuppliesApplyNum(int suppliesApplyNum) {
        this.suppliesApplyNum = suppliesApplyNum;
    }

    public int getTemInfoNum() {
        return temInfoNum;
    }

    public void setTemInfoNum(int temInfoNum) {
        this.temInfoNum = temInfoNum;
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

    public List<PicUrl> getPicUrls() {
        return picUrls;
    }

    public void setPicUrls(List<PicUrl> picUrls) {
        this.picUrls = picUrls;
    }
}
