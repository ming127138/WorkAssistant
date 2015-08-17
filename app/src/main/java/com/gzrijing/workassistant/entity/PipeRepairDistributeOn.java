package com.gzrijing.workassistant.entity;

public class PipeRepairDistributeOn {
    private String id;
    private boolean urgent;
    private String info2;
    private String info3;
    private String info4;

    public PipeRepairDistributeOn() {
    }

    public PipeRepairDistributeOn(String id, boolean urgent, String info2, String info3, String info4) {
        this.id = id;
        this.urgent = urgent;
        this.info2 = info2;
        this.info3 = info3;
        this.info4 = info4;
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

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }
}
