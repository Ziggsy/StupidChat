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
    public List<ChatUiModel> mChatUiModel;

    private static final DataHolderApp ourInstance = new DataHolderApp();

    public static DataHolderApp getInstance() {
        return ourInstance;
    }

    private DataHolderApp() {
    }

    public List<ContactUiModel> getContactUiModel() {
        if (mContactUiModel == null) {
            return new ArrayList<ContactUiModel>();
        } else {
            return mContactUiModel;
        }
    }

    public void setContactUiModel(List<ContactUiModel> data) {
        mContactUiModel = data;
    }

}
