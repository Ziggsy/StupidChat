package com.upgradedsoftware.android.chat.utils;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Helper {
    private static final Helper ourInstance = new Helper();

    public static Helper getInstance() {
        return ourInstance;
    }

    public static final String URL_MY_AVATAR = "https://pp.userapi.com/c852224/v852224410/fbe3b/3B4yGYVmwuM.jpg";

    public static final String JSON_SERVER_RESPONSE = "ServerResponse.json";
    public static final String JSON_CHAT_MESSAGES = "ChatMessages";
    public static final String JSON_NEW_CHATTERS = "NewChat";
    public static final String JSON_NEW_MESSAGES = "FakeMessages";

    public static final String KEY_MESSAGE_MESSAGES = "messages";
    public static final String KEY_MESSAGE_ID = "id";
    public static final String KEY_MESSAGE_FROM_ME = "from_me";
    public static final String KEY_MESSAGE_TEXT = "text";
    public static final String KEY_MESSAGE_CREATED = "created";

    public static final String KEY_CHAT_CHATS = "chats";
    public static final String KEY_CHAT_ID = "id";
    public static final String KEY_CHAT_UPDATED = "updated";
    public static final String KEY_CHAT_CREATED = "created";
    public static final String KEY_CHAT_UNREAD = "unread";
    public static final String KEY_CHAT_WITH_USER = "with_user";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_SETTINGS = "settings";
    public static final String KEY_USER_AVATAR = "avatar";
    public static final String KEY_SETTING_WORK = "work";
    public static final String KEY_AVATAR_URL = "url";

    private Helper() {
    }

    public JSONObject initJSON(Activity activity, String way) {
        try {
            InputStream is = activity.getAssets().open(way);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
