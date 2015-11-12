package com.gzrijing.workassistant.entity;

public class User {
    private String userNo;
    private String userName;
    private String userDept;
    private String userSit;
    private String userRank;

    public User() {
    }

    public User(String userNo, String userName, String userDept, String userSit, String userRank) {
        this.userNo = userNo;
        this.userName = userName;
        this.userDept = userDept;
        this.userSit = userSit;
        this.userRank = userRank;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    public String getUserSit() {
        return userSit;
    }

    public void setUserSit(String userSit) {
        this.userSit = userSit;
    }

    public String getUserRank() {
        return userRank;
    }

    public void setUserRank(String userRank) {
        this.userRank = userRank;
    }
}
