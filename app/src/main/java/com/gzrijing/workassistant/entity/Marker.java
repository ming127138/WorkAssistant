package com.gzrijing.workassistant.entity;

public class Marker {
    private String id;
    private double latitude;
    private double longitude;
    private int tag;
    private String info;

    public Marker() {
    }

    public Marker(String id, double latitude, double longitude, int tag, String info) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tag = tag;
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
