package com.upgradedsoftware.android.chat.utils;

import android.graphics.Bitmap;

import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ContactUiModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class DataHolder {
    public JSONObject mJSONObjectContact;
    public JSONObject mJSONObjectMessages;
    public List<ContactUiModel> mContactUiModel;
    public List<ChatUiModel> mChatUiModel;
    public HashMap<String, Bitmap> imageMap;

    private static final DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }
}
