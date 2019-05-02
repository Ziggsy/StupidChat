package com.upgradedsoftware.android.chat.data;

import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ContactUiModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataHolderApp {
    public HashMap<String, JSONObject> chatsMap;
    private List<ContactUiModel> mContactUiModel;
    public HashMap<String, List<ChatUiModel>> mChatUiMap = new HashMap<>();
    public HashMap<String, List<ChatUiModel>> mCacheChatUiMap = new HashMap<>();

    private static final DataHolderApp ourInstance = new DataHolderApp();

    public static DataHolderApp getInstance() {
        return ourInstance;
    }

    private DataHolderApp() {
    }

    public List<ContactUiModel> getContactUiModel() {
        if (mContactUiModel == null) {
            return new ArrayList<>();
        } else {
            return mContactUiModel;
        }
    }

    public void setContactUiModel(List<ContactUiModel> data) {
        mContactUiModel = data;
    }

    public void saveCachedMessage(ChatUiModel chatUiModel, String id) {
        if (mCacheChatUiMap.get(id) == null){
            mCacheChatUiMap.put(id, new ArrayList<ChatUiModel>());
        }
        mCacheChatUiMap.get(id).add(chatUiModel);
    }

    public List<ChatUiModel> getCachedData(String id) {
        if(mCacheChatUiMap.get(id) == null){
            mCacheChatUiMap.put(id, new ArrayList<ChatUiModel>());
        }
        return mCacheChatUiMap.get(id);
    }
}
