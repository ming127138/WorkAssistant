package com.gzrijing.workassistant.entity;

public class SafetyInspectSecondItem {
    private String id;
    private String name;
    private boolean isCheck;

    public SafetyInspectSecondItem() {
    }

    public SafetyInspectSecondItem(String id, String name, boolean isCheck) {
        this.id = id;
        this.name = name;
        this.isCheck = isCheck;
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

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
