package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SendMachine implements Parcelable {
    private String orderId;         //工程单号
    private String billNo;          //机械申请单号
    private String address;         //用机械地址
    private String machineNo;       //机械编号
    private String machineName;     //机械名称
    private String applyName;       //申请人
    private String sendData;        //发放时间

    public SendMachine() {
    }

    public SendMachine(String orderId, String billNo, String address, String machineNo, String machineName, String applyName, String sendData) {
        this.orderId = orderId;
        this.billNo = billNo;
        this.address = address;
        this.machineNo = machineNo;
        this.machineName = machineName;
        this.applyName = applyName;
        this.sendData = sendData;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.billNo);
        dest.writeString(this.address);
        dest.writeString(this.machineNo);
        dest.writeString(this.machineName);
        dest.writeString(this.applyName);
        dest.writeString(this.sendData);
    }

    protected SendMachine(Parcel in) {
        this.orderId = in.readString();
        this.billNo = in.readString();
        this.address = in.readString();
        this.machineNo = in.readString();
        this.machineName = in.readString();
        this.applyName = in.readString();
        this.sendData = in.readString();
    }

    public static final Creator<SendMachine> CREATOR = new Creator<SendMachine>() {
        public SendMachine createFromParcel(Parcel source) {
            return new SendMachine(source);
        }

        public SendMachine[] newArray(int size) {
            return new SendMachine[size];
        }
    };
}
