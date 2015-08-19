package com.gzrijing.workassistant.entity;

import java.io.Serializable;

public class Subordinate implements Serializable{
    private String name;
    private boolean isCheck;

    public Subordinate() {
    }

    public Subordinate(String name, boolean isCheck) {
        this.name = name;
        this.isCheck = isCheck;
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

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }
}
