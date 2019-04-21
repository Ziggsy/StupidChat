package com.upgradedsoftware.android.chat.models;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    private String userId;
    private String name;
    private UserSettings settings;
    private UserAvatars userAvatars;

    public UserModel(String userId, String name, UserSettings settings, UserAvatars avatars){
        this.userId = userId;
        this.name = name;
        this.settings = settings;
        this.userAvatars = avatars;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public UserSettings getSettings() {
        return settings;
    }

    public UserAvatars getUserAvatars() {
        return userAvatars;
    }
}
