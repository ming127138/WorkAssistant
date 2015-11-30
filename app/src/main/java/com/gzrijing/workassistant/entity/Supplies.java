package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Supplies implements Parcelable {
    private int dataId;         //数据库id
    private String id;          //材料编号
    private String name;        //材料名称
    private String spec;        //材料规格
    private String unit;        //材料单位
    private int num;            //数量
    private String state;       //申请材料状态(申请中，已审批，领用)
    private boolean isCheck;    //是否选择机械退回

    public Supplies() {
    }

    public Supplies(int dataId, String id, String name, String spec, String unit, int num, String state, boolean isCheck) {
        this.dataId = dataId;
        this.id = id;
        this.name = name;
        this.spec = spec;
        this.unit = unit;
        this.num = num;
        this.state = state;
        this.isCheck = isCheck;
    }

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
        dest.writeInt(this.dataId);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.spec);
        dest.writeString(this.unit);
        dest.writeInt(this.num);
        dest.writeString(this.state);
        dest.writeByte(isCheck ? (byte) 1 : (byte) 0);
    }

    protected Supplies(Parcel in) {
        this.dataId = in.readInt();
        this.id = in.readString();
        this.name = in.readString();
        this.spec = in.readString();
        this.unit = in.readString();
        this.num = in.readInt();
        this.state = in.readString();
        this.isCheck = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Supplies> CREATOR = new Parcelable.Creator<Supplies>() {
        public Supplies createFromParcel(Parcel source) {
            return new Supplies(source);
        }

        public Supplies[] newArray(int size) {
            return new Supplies[size];
        }
    };
}
