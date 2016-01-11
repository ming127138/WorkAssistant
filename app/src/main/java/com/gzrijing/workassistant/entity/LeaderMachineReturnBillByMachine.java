package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class LeaderMachineReturnBillByMachine implements Parcelable {
    private String machineNo;   //机械编号
    private String name;        //机械名称
    private String unit;        //机械单位

    public LeaderMachineReturnBillByMachine() {
    }

    public LeaderMachineReturnBillByMachine(String machineNo, String name, String unit) {
        this.machineNo = machineNo;
        this.name = name;
        this.unit = unit;
    }

    public String getMachineNo() {
        return machineNo;
    }

    public void setMachineNo(String machineNo) {
        this.machineNo = machineNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.machineNo);
        dest.writeString(this.name);
        dest.writeString(this.unit);
    }

    protected LeaderMachineReturnBillByMachine(Parcel in) {
        this.machineNo = in.readString();
        this.name = in.readString();
        this.unit = in.readString();
    }

    public static final Creator<LeaderMachineReturnBillByMachine> CREATOR = new Creator<LeaderMachineReturnBillByMachine>() {
        public LeaderMachineReturnBillByMachine createFromParcel(Parcel source) {
            return new LeaderMachineReturnBillByMachine(source);
        }

        public LeaderMachineReturnBillByMachine[] newArray(int size) {
            return new LeaderMachineReturnBillByMachine[size];
        }
    };
}
