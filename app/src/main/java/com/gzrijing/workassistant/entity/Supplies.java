package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Supplies implements Parcelable {
    private String id;          //材料编号
    private String name;        //材料名称
    private String spec;        //材料规格
    private String unit;        //材料单位
    private String num;         //数量

    public Supplies() {
    }

    public Supplies(String id, String name, String spec, String unit, String num) {
        this.id = id;
        this.name = name;
        this.spec = spec;
        this.unit = unit;
        this.num = num;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.spec);
        dest.writeString(this.unit);
        dest.writeString(this.num);
    }

    protected Supplies(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.spec = in.readString();
        this.unit = in.readString();
        this.num = in.readString();
    }

    public static final Creator<Supplies> CREATOR = new Creator<Supplies>() {
        public Supplies createFromParcel(Parcel source) {
            return new Supplies(source);
        }

        public Supplies[] newArray(int size) {
            return new Supplies[size];
        }
    };
}
