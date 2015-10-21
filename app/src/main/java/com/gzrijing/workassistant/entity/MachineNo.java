package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MachineNo implements Parcelable {
    private String applyId;
    private String applyTime;
    private String state;
    private String useTime;
    private String returnTime;
    private String useAddress;
    private String remarks;

    public MachineNo() {
    }

    public MachineNo(String applyId, String applyTime, String state, String useTime, String returnTime, String useAddress, String remarks) {
        this.applyId = applyId;
        this.applyTime = applyTime;
        this.state = state;
        this.useTime = useTime;
        this.returnTime = returnTime;
        this.useAddress = useAddress;
        this.remarks = remarks;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.applyId);
        dest.writeString(this.applyTime);
        dest.writeString(this.state);
        dest.writeString(this.useTime);
        dest.writeString(this.returnTime);
        dest.writeString(this.useAddress);
        dest.writeString(this.remarks);
    }

    protected MachineNo(Parcel in) {
        this.applyId = in.readString();
        this.applyTime = in.readString();
        this.state = in.readString();
        this.useTime = in.readString();
        this.returnTime = in.readString();
        this.useAddress = in.readString();
        this.remarks = in.readString();
    }

    public static final Parcelable.Creator<MachineNo> CREATOR = new Parcelable.Creator<MachineNo>() {
        public MachineNo createFromParcel(Parcel source) {
            return new MachineNo(source);
        }

        public MachineNo[] newArray(int size) {
            return new MachineNo[size];
        }
    };
}
