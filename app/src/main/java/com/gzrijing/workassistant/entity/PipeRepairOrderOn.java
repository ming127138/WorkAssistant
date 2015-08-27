package com.gzrijing.workassistant.entity;

public class PipeRepairOrderOn {
    private String id;
    private boolean urgent;
    private String info1;
    private String info2;
    private String info3;
    private String deadline;

    public PipeRepairOrderOn() {
    }

    public PipeRepairOrderOn(String id, boolean urgent, String info1, String info2, String info3, String deadline) {
        this.id = id;
        this.urgent = urgent;
        this.info1 = info1;
        this.info2 = info2;
        this.info3 = info3;
        this.deadline = deadline;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
