package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Subordinate implements Parcelable {
    private String userNo;
    private String name;
    private boolean isCheck;

    public Subordinate() {
    }

    public Subordinate(String userNo, String name, boolean isCheck) {
        this.userNo = userNo;
        this.name = name;
        this.isCheck = isCheck;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(this.userNo);
        dest.writeString(this.name);
        dest.writeByte(isCheck ? (byte) 1 : (byte) 0);
    }

    protected Subordinate(Parcel in) {
        this.userNo = in.readString();
        this.name = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Subordinate> CREATOR = new Parcelable.Creator<Subordinate>() {
        public Subordinate createFromParcel(Parcel source) {
            return new Subordinate(source);
        }

        public Subordinate[] newArray(int size) {
            return new Subordinate[size];
        }
    };
}
