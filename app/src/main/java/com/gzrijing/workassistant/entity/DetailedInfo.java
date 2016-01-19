package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailedInfo implements Parcelable {
    private String key;
    private String value;

    public DetailedInfo() {
    }

    public DetailedInfo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.value);
    }

    protected DetailedInfo(Parcel in) {
        this.key = in.readString();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<DetailedInfo> CREATOR = new Parcelable.Creator<DetailedInfo>() {
        public DetailedInfo createFromParcel(Parcel source) {
            return new DetailedInfo(source);
        }

        public DetailedInfo[] newArray(int size) {
            return new DetailedInfo[size];
        }
    };
}
