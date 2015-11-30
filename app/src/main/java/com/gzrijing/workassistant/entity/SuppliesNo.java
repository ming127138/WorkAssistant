package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SuppliesNo implements Parcelable {
    private String applyId;
    private String applyTime;
    private String state;
    private String useTime;
    private String remarks;

    public SuppliesNo() {
    }

    public SuppliesNo(String applyId, String applyTime, String state, String useTime, String remarks) {
        this.applyId = applyId;
        this.applyTime = applyTime;
        this.state = state;
        this.useTime = useTime;
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
        dest.writeString(this.remarks);
    }

    protected SuppliesNo(Parcel in) {
        this.applyId = in.readString();
        this.applyTime = in.readString();
        this.state = in.readString();
        this.useTime = in.readString();
        this.remarks = in.readString();
    }

    public static final Parcelable.Creator<SuppliesNo> CREATOR = new Parcelable.Creator<SuppliesNo>() {
        public SuppliesNo createFromParcel(Parcel source) {
            return new SuppliesNo(source);
        }

        public SuppliesNo[] newArray(int size) {
            return new SuppliesNo[size];
        }
    };
}
