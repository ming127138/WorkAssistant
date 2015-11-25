package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class PicUrl implements Parcelable {
    private String picUrl;
    private boolean isCheck;

    public PicUrl() {
    }

    public PicUrl(String picUrl, boolean isCheck) {
        this.picUrl = picUrl;
        this.isCheck = isCheck;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.picUrl);
        dest.writeByte(isCheck ? (byte) 1 : (byte) 0);
    }

    protected PicUrl(Parcel in) {
        this.picUrl = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PicUrl> CREATOR = new Parcelable.Creator<PicUrl>() {
        public PicUrl createFromParcel(Parcel source) {
            return new PicUrl(source);
        }

        public PicUrl[] newArray(int size) {
            return new PicUrl[size];
        }
    };
}
