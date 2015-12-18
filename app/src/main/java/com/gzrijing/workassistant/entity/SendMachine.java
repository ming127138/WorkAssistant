package com.gzrijing.workassistant.entity;

public class SendMachine {
    private String billNo;          //机械申请单号
    private String address;         //用机械地址
    private String machineNo;       //机械编号
    private String machineName;     //机械名称

    public SendMachine() {
    }

    public SendMachine(String billNo, String address, String machineNo, String machineName) {
        this.billNo = billNo;
        this.address = address;
        this.machineNo = machineNo;
        this.machineName = machineName;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
}
