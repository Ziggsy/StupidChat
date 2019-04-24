package com.upgradedsoftware.android.chat.models;

public class UserModelShort {
    private String userId;
    private String name;
    private String userInfo;

    public UserModelShort(String userId, String name, String userInfo){
        this.userId = userId;
        this.name = name;
        this.userInfo = userInfo;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getUserInfo() {
        return userInfo;
    }
}
