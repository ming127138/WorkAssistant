package com.gzrijing.workassistant.entity;

public class User {
    private String UserName;

    public User() {
    }

    public User(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "UserName='" + UserName + '\'' +
                '}';
    }
}
