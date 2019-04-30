package com.upgradedsoftware.android.chat.utils;

import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.models.UserAvatars;
import com.upgradedsoftware.android.chat.models.UserModel;
import com.upgradedsoftware.android.chat.models.UserSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.upgradedsoftware.android.chat.utils.Helper.KEY_AVATAR_URL;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CHATS;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CREATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UNREAD;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UPDATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_WITH_USER;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_SETTING_WORK;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_AVATAR;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_NAME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_SETTINGS;

public class DataHolder {
    public JSONObject mJSONObjectContact;
    public JSONObject mJSONObjectMessages;
    public List<ContactUiModel> mContactUiModel;
    public List<ChatUiModel> mChatUiModel;
    private int counter;

    private static final DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter() {
        counter++;
    }

    public static void saveToServerModel() throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < DataHolder.getInstance().mContactUiModel.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(KEY_MESSAGE_ID, DataHolder.getInstance().mContactUiModel.get(i).getChatId());
            jsonObject.put(KEY_CHAT_UPDATED, DataHolder.getInstance().mContactUiModel.get(i).getUpdated());
            jsonObject.put(KEY_CHAT_CREATED, DataHolder.getInstance().mContactUiModel.get(i).getCreated());
            jsonObject.put(KEY_CHAT_UNREAD, DataHolder.getInstance().mContactUiModel.get(i).getUnread());
            jsonObject.put(KEY_CHAT_WITH_USER, mapUserModel(DataHolder.getInstance().mContactUiModel.get(i).getUser()));
            array.put(jsonObject);
        }
        JSONObject object = new JSONObject();
        DataHolder.getInstance().mJSONObjectContact = object.put(KEY_CHAT_CHATS, array);
    }

    private static JSONObject mapUserModel(UserModel user) throws JSONException {
        JSONObject userJsonObject = new JSONObject();
        userJsonObject.put(KEY_USER_ID, user.getUserId());
        userJsonObject.put(KEY_USER_NAME, user.getName());
        userJsonObject.put(KEY_USER_SETTINGS, mapUserSetting(user.getSettings()));
        userJsonObject.put(KEY_USER_AVATAR, mapUserAvatar(user.getUserAvatars()));
        return userJsonObject;
    }

    private static JSONObject mapUserSetting(UserSettings settings) throws JSONException {
        JSONObject settingJsonObject = new JSONObject();
        settingJsonObject.put(KEY_SETTING_WORK, settings.getWork());
        return settingJsonObject;
    }

    private static JSONObject mapUserAvatar(UserAvatars avatar) throws JSONException {
        JSONObject avatarJsonObject = new JSONObject();
        avatarJsonObject.put(KEY_AVATAR_URL, avatar.getUrl());
        return avatarJsonObject;
    }
}
