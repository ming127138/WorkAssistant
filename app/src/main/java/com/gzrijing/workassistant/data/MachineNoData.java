package com.gzrijing.workassistant.data;

import org.litepal.crud.DataSupport;

public class MachineNoData extends DataSupport {
    private int id;
    private String applyId;         //机械申请单号
    private String applyTime;       //机械申请时间
    private String state;           //机械申请状态
    private String useTime;         //机械领用时间
    private String returnTime;      //机械退回时间
    private String useAddress;      //机械使用地点
    private String remarks;         //备注
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

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getUseAddress() {
        return useAddress;
    }

    public void setUseAddress(String useAddress) {
        this.useAddress = useAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BusinessData getBusinessData() {
        return businessData;
    }

    public void setBusinessData(BusinessData businessData) {
        this.businessData = businessData;
    }
}
