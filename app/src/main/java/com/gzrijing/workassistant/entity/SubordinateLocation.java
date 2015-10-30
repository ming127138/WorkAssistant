package com.gzrijing.workassistant.entity;

public class SubordinateLocation {
    private double latitude;
    private double longitude;
    private String name;
    private String tel;
    private String lastTime;

    public SubordinateLocation() {
    }

    public SubordinateLocation(double latitude, double longitude, String name, String tel, String lastTime) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.tel = tel;
        this.lastTime = lastTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
}
