package com.gzrijing.workassistant.entity;

public class PipeRepairDistributeOff {
    private String id;
    private boolean urgent;
    private String info1;
    private String info2;
    private String info3;
    private String time;
    private boolean flag;

    public PipeRepairDistributeOff() {
    }

    public PipeRepairDistributeOff(String id, boolean urgent, String info1, String info2, String info3, String time, boolean flag) {
        this.id = id;
        this.urgent = urgent;
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.time = time;
        this.flag = flag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo3() {
        return info3;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
