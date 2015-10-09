package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class SuppliesVerify implements Parcelable {
    private String id;          //材料申请单号
    private String applicant;   //申请人
    private String useTime;     //领用时间
    private String remarks;     //备注

    public SuppliesVerify() {
    }

    public SuppliesVerify(String id, String applicant, String useTime, String remarks) {
        this.id = id;
        this.applicant = applicant;
        this.useTime = useTime;
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
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
        dest.writeString(this.id);
        dest.writeString(this.applicant);
        dest.writeString(this.useTime);
        dest.writeString(this.remarks);
    }

    protected SuppliesVerify(Parcel in) {
        this.id = in.readString();
        this.applicant = in.readString();
        this.useTime = in.readString();
        this.remarks = in.readString();
    }

    public static final Parcelable.Creator<SuppliesVerify> CREATOR = new Parcelable.Creator<SuppliesVerify>() {
        public SuppliesVerify createFromParcel(Parcel source) {
            return new SuppliesVerify(source);
        }

        public SuppliesVerify[] newArray(int size) {
            return new SuppliesVerify[size];
        }
    };
}
