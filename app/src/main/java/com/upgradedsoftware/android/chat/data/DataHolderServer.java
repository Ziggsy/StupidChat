package com.upgradedsoftware.android.chat.data;

import com.upgradedsoftware.android.chat.models.UserAvatars;
import com.upgradedsoftware.android.chat.models.UserModel;
import com.upgradedsoftware.android.chat.models.UserSettings;
import com.upgradedsoftware.android.chat.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.upgradedsoftware.android.chat.utils.Helper.KEY_AVATAR_URL;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CHATS;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CREATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UNREAD;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UPDATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_WITH_USER;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_SETTING_WORK;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_AVATAR;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_NAME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_SETTINGS;

public class DataHolderServer {
    public JSONObject mJSONObjectContact;
    public JSONObject mJSONObjectMessages;
    private HashMap<String, JSONObject> messagesMap;
    private int counter;

    private static final DataHolderServer ourInstance = new DataHolderServer();

    public static DataHolderServer getInstance() {
        return ourInstance;
    }

    private DataHolderServer() {
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter() {
        counter++;
    }

    private void initMessagesMap() throws JSONException {
        HashMap<String, JSONObject> newMap = new HashMap<>();
        JSONArray chatsArray = mJSONObjectContact.getJSONArray(KEY_CHAT_CHATS);
        for (int i = 0; i < chatsArray.length(); i++){
            JSONObject chat = (JSONObject) chatsArray.get(i);
            String chatID = (String) chat.get(KEY_CHAT_ID);
            newMap.put(chatID, Helper.getInstance().initJSON(chatID));
        }
        messagesMap = newMap;
    }

    public JSONObject getMessagesFormChat(String chatID) throws JSONException {
        if (messagesMap == null){
            initMessagesMap();
        }
        if (messagesMap.get(chatID) == null){
            messagesMap.put(chatID, Helper.getInstance().initJSON(chatID));
            //TODO если initJson вернет null, то надо это обработать
            return messagesMap.get(chatID);
        } else {
           return messagesMap.get(chatID);
        }
    }

    public static void saveToServerModel() throws JSONException {
        JSONArray array = new JSONArray();
        for (int i = 0; i < DataHolderApp.getInstance().getContactUiModel().size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(KEY_MESSAGE_ID, DataHolderApp.getInstance().getContactUiModel().get(i).getChatId());
            jsonObject.put(KEY_CHAT_UPDATED, DataHolderApp.getInstance().getContactUiModel().get(i).getUpdated());
            jsonObject.put(KEY_CHAT_CREATED, DataHolderApp.getInstance().getContactUiModel().get(i).getCreated());
            jsonObject.put(KEY_CHAT_UNREAD, DataHolderApp.getInstance().getContactUiModel().get(i).getUnread());
            jsonObject.put(KEY_CHAT_WITH_USER, mapUserModel(DataHolderApp.getInstance().getContactUiModel().get(i).getUser()));
            array.put(jsonObject);
        }
        JSONObject object = new JSONObject();
        DataHolderServer.getInstance().mJSONObjectContact = object.put(KEY_CHAT_CHATS, array);
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