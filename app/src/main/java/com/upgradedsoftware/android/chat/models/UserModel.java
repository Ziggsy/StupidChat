package com.upgradedsoftware.android.chat.models;

public class UserModel {
    private final String userId;
    private final String name;
    private final UserSettings settings;
    private final UserAvatars userAvatars;

    public UserModel(String userId, String name, UserSettings settings, UserAvatars avatars) {
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
