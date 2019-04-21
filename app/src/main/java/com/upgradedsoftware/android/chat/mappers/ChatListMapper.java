package com.upgradedsoftware.android.chat.mappers;

import com.upgradedsoftware.android.chat.models.ChatsUiModel;
import com.upgradedsoftware.android.chat.models.UserAvatars;
import com.upgradedsoftware.android.chat.models.UserModel;
import com.upgradedsoftware.android.chat.models.UserSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ChatListMapper {

    public static String KEY_CHAT_CHATS = "chats";
    public static String KEY_CHAT_ID = "id";
    public static String KEY_CHAT_UPDATED = "updated";
    public static String KEY_CHAT_CREATED = "created";
    public static String KEY_CHAT_UNREAD = "unread";
    public static String KEY_CHAT_WITH_USER = "with_user";
    public static String KEY_USER_ID = "id";
    public static String KEY_USER_NAME = "name";
    public static String KEY_USER_SETTINGS = "settings";
    public static String KEY_USER_AVATAR = "avatar";
    public static String KEY_SETTING_WORK = "work";
    public static String KEY_AVATAR_URL = "url";

    public ArrayList<ChatsUiModel> mapToUI(JSONObject response) throws JSONException {
        ArrayList<ChatsUiModel> list = new ArrayList<>();

        JSONArray chatsArray = response.getJSONArray(KEY_CHAT_CHATS);

        for (int i = 0; i < chatsArray.length(); i++) {
            JSONObject jsonElement = chatsArray.getJSONObject(i);
            ChatsUiModel element = new ChatsUiModel(
                    jsonElement.getString(KEY_CHAT_ID),
                    jsonElement.getLong(KEY_CHAT_UPDATED),
                    jsonElement.getLong(KEY_CHAT_CREATED),
                    jsonElement.getBoolean(KEY_CHAT_UNREAD),
                    parseUser(jsonElement.getJSONObject(KEY_CHAT_WITH_USER))
            );
            list.add(element);
        }

        return list;
    }

    private UserModel parseUser(JSONObject usersInChatArray) throws JSONException {
        UserModel element = new UserModel(
                usersInChatArray.getString(KEY_USER_ID),
                usersInChatArray.getString(KEY_USER_NAME),
                parseSettings(usersInChatArray.getJSONObject(KEY_USER_SETTINGS)),
                parseAvatar(usersInChatArray.getJSONObject(KEY_USER_AVATAR))
        );
        return element;
    }

    private UserSettings parseSettings(JSONObject settings) throws JSONException {
        return new UserSettings(
                settings.getString(KEY_SETTING_WORK)
        );
    }

    private UserAvatars parseAvatar(JSONObject avatar) throws JSONException {
        return new UserAvatars(
                avatar.getString(KEY_AVATAR_URL)
        );
    }
}
