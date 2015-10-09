package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class MachineVerify implements Parcelable {
    private String id;          //机械申请单号
    private String applicant;   //申请人
    private String useTime;     //领用时间
    private String returnTime;  //退回时间
    private String useAdress;   //使用地点
    private String remarks;     //备注

    public MachineVerify() {
    }

    public MachineVerify(String id, String applicant, String useTime, String returnTime, String useAdress, String remarks) {
        this.id = id;
        this.applicant = applicant;
        this.useTime = useTime;
        this.returnTime = returnTime;
        this.useAdress = useAdress;
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

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getUseAdress() {
        return useAdress;
    }

    public void setUseAdress(String useAdress) {
        this.useAdress = useAdress;
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
        dest.writeString(this.returnTime);
        dest.writeString(this.useAdress);
        dest.writeString(this.remarks);
    }

    protected MachineVerify(Parcel in) {
        this.id = in.readString();
        this.applicant = in.readString();
        this.useTime = in.readString();
        this.returnTime = in.readString();
        this.useAdress = in.readString();
        this.remarks = in.readString();
    }

    public static final Parcelable.Creator<MachineVerify> CREATOR = new Parcelable.Creator<MachineVerify>() {
        public MachineVerify createFromParcel(Parcel source) {
            return new MachineVerify(source);
        }

        public MachineVerify[] newArray(int size) {
            return new MachineVerify[size];
        }
    };
}
