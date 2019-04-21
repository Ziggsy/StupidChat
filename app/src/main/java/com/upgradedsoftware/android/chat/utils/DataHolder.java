package com.upgradedsoftware.android.chat.utils;

import android.graphics.Bitmap;
import android.media.Image;

import com.upgradedsoftware.android.chat.models.ChatsUiModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class DataHolder {
    public JSONObject mJSONObject;
    public List<ChatsUiModel> mChatsUiModel;
    public HashMap<String, Bitmap> imageMap;

    private static final DataHolder ourInstance = new DataHolder();

    public static DataHolder getInstance() {
        return ourInstance;
    }

    private DataHolder() {
    }
}
