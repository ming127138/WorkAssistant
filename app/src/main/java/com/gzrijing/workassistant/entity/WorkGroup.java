package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class WorkGroup implements Parcelable {
    private String groupNo;     //工作组编号
    private String groupName;   //工作组名称
    private boolean isCheck;    //是否选中

    public WorkGroup() {
    }

    public WorkGroup(String groupNo, String groupName, boolean isCheck) {
        this.groupNo = groupNo;
        this.groupName = groupName;
        this.isCheck = isCheck;
    }

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupNo);
        dest.writeString(this.groupName);
        dest.writeByte(isCheck ? (byte) 1 : (byte) 0);
    }

    protected WorkGroup(Parcel in) {
        this.groupNo = in.readString();
        this.groupName = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Creator<WorkGroup> CREATOR = new Creator<WorkGroup>() {
        @Override
        public WorkGroup createFromParcel(Parcel source) {
            return new WorkGroup(source);
        }

        @Override
        public WorkGroup[] newArray(int size) {
            return new WorkGroup[size];
        }
    };
}
