package com.gzrijing.workassistant.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Acceptance implements Parcelable {
    private String orderId;             //工程编号
    private String orderType;           //工程类型
    private ArrayList<Supplies> suppliesByClient = new ArrayList<Supplies>();       //客户收费材料
    private ArrayList<Supplies> suppliesByWater = new ArrayList<Supplies>();        //水务收费材料
    private ArrayList<DetailedInfo> detailedInfos = new ArrayList<DetailedInfo>();  //详细信息

    public Acceptance() {
    }

    public Acceptance(String orderId, String orderType, ArrayList<Supplies> suppliesByClient, ArrayList<Supplies> suppliesByWater, ArrayList<DetailedInfo> detailedInfos) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.suppliesByClient = suppliesByClient;
        this.suppliesByWater = suppliesByWater;
        this.detailedInfos = detailedInfos;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public ArrayList<Supplies> getSuppliesByClient() {
        return suppliesByClient;
    }

    public void setSuppliesByClient(ArrayList<Supplies> suppliesByClient) {
        this.suppliesByClient = suppliesByClient;
    }

    public ArrayList<Supplies> getSuppliesByWater() {
        return suppliesByWater;
    }

    public void setSuppliesByWater(ArrayList<Supplies> suppliesByWater) {
        this.suppliesByWater = suppliesByWater;
    }

    public ArrayList<DetailedInfo> getDetailedInfos() {
        return detailedInfos;
    }

    public void setDetailedInfos(ArrayList<DetailedInfo> detailedInfos) {
        this.detailedInfos = detailedInfos;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.orderType);
        dest.writeTypedList(suppliesByClient);
        dest.writeTypedList(suppliesByWater);
        dest.writeTypedList(detailedInfos);
    }

    protected Acceptance(Parcel in) {
        this.orderId = in.readString();
        this.orderType = in.readString();
        this.suppliesByClient = in.createTypedArrayList(Supplies.CREATOR);
        this.suppliesByWater = in.createTypedArrayList(Supplies.CREATOR);
        this.detailedInfos = in.createTypedArrayList(DetailedInfo.CREATOR);
    }

    public static final Creator<Acceptance> CREATOR = new Creator<Acceptance>() {
        public Acceptance createFromParcel(Parcel source) {
            return new Acceptance(source);
        }

        public Acceptance[] newArray(int size) {
            return new Acceptance[size];
        }
    };
}
