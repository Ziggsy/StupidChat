package com.upgradedsoftware.android.chat.models;

public class UserModelShort {
    private final String userId;
    private final String name;
    private final String userInfo;
    private final String url;

    public UserModelShort(String userId, String name, String userInfo, String url){
        this.userId = userId;
        this.name = name;
        this.userInfo = userInfo;
        this.url = url;
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

    public String getUrl() {
        return url;
    }
}
