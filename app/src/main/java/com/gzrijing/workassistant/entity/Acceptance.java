package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Acceptance implements Parcelable {
    private String orderId;     //工程编号
    private String content;     //施工内容
    private String civil;       //土建项目
    private String checkDate;   //审核日期
    private String state;       //状态

    public Acceptance() {
    }

    public Acceptance(String orderId, String content, String civil, String checkDate, String state) {
        this.orderId = orderId;
        this.content = content;
        this.civil = civil;
        this.checkDate = checkDate;
        this.state = state;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCivil() {
        return civil;
    }

    public void setCivil(String civil) {
        this.civil = civil;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.content);
        dest.writeString(this.civil);
        dest.writeString(this.checkDate);
        dest.writeString(this.state);
    }

    protected Acceptance(Parcel in) {
        this.orderId = in.readString();
        this.content = in.readString();
        this.civil = in.readString();
        this.checkDate = in.readString();
        this.state = in.readString();
    }

    public static final Parcelable.Creator<Acceptance> CREATOR = new Parcelable.Creator<Acceptance>() {
        public Acceptance createFromParcel(Parcel source) {
            return new Acceptance(source);
        }

        public Acceptance[] newArray(int size) {
            return new Acceptance[size];
        }
    };
}
