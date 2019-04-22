package com.upgradedsoftware.android.chat.mappers;

import com.upgradedsoftware.android.chat.models.ChatsUiModel;
import com.upgradedsoftware.android.chat.models.UserAvatars;
import com.upgradedsoftware.android.chat.models.UserModel;
import com.upgradedsoftware.android.chat.models.UserSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.upgradedsoftware.android.chat.utils.Helper.KEY_AVATAR_URL;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CHATS;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CREATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UNREAD;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UPDATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_WITH_USER;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_SETTING_WORK;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_AVATAR;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_NAME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_SETTINGS;


public class ChatListMapper {


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
