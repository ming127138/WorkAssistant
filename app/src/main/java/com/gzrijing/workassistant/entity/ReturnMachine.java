package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ReturnMachine implements Parcelable {
    private String orderId;         //工程单号
    private String billNo;          //机械申请单号
    private String address;         //退机械地址
    private String type;            //退回类型
    private String machineName;     //机械名称
    private String returnName;      //退机人
    private String returnTiem;      //退机时间

    public ReturnMachine() {
    }

    public ReturnMachine(String orderId, String billNo, String address, String type, String machineName, String returnName, String returnTiem) {
        this.orderId = orderId;
        this.billNo = billNo;
        this.address = address;
        this.type = type;
        this.machineName = machineName;
        this.returnName = returnName;
        this.returnTiem = returnTiem;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getReturnTiem() {
        return returnTiem;
    }

    public void setReturnTiem(String returnTiem) {
        this.returnTiem = returnTiem;
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
        dest.writeString(this.type);
        dest.writeString(this.machineName);
        dest.writeString(this.returnName);
        dest.writeString(this.returnTiem);
    }

    protected ReturnMachine(Parcel in) {
        this.orderId = in.readString();
        this.billNo = in.readString();
        this.address = in.readString();
        this.type = in.readString();
        this.machineName = in.readString();
        this.returnName = in.readString();
        this.returnTiem = in.readString();
    }

    public static final Creator<ReturnMachine> CREATOR = new Creator<ReturnMachine>() {
        public ReturnMachine createFromParcel(Parcel source) {
            return new ReturnMachine(source);
        }

        public ReturnMachine[] newArray(int size) {
            return new ReturnMachine[size];
        }
    };
}