package com.upgradedsoftware.android.chat.data;

import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ContactUiModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataHolderApp {
    private List<ContactUiModel> mContactUiModel;
    private static final HashMap<String, List<ChatUiModel>> mChatUiMap = new HashMap<>();

    private static final DataHolderApp ourInstance = new DataHolderApp();

    private DataHolderApp() {
    }

    public static DataHolderApp getInstance() {
        return ourInstance;
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

    public List<ChatUiModel> getMessageList(String id) {
        if (mChatUiMap.get(id) == null) {
            mChatUiMap.put(id, new ArrayList<ChatUiModel>());
        }
        return mChatUiMap.get(id);
    }

    public void setMessageList(List<ChatUiModel> chatUiMap, String id) {
        mChatUiMap.put(id, chatUiMap);
    }

}
