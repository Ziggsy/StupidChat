package com.upgradedsoftware.android.chat.utils;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.upgradedsoftware.android.chat.App;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Helper {
    private static final Helper ourInstance = new Helper();

    public static Helper getInstance() {
        return ourInstance;
    }

    public static final String URL_MY_AVATAR = "https://pp.userapi.com/c852224/v852224410/fbe3b/3B4yGYVmwuM.jpg";

    public static final int SERVER_FAKE_LATENCY = 5;

    public static final String BOTTOM_SHEET_TAG = "bottom_sheet_tag";

    public static final String JSON_SERVER_RESPONSE = "ServerResponse.json";
    public static final String JSON_NEW_CHATTERS = "NewChat";

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
    public static final String KEY_CHAT_LAST_MESSAGE = "last_message";
    public static final String KEY_CHAT_WITH_USER = "with_user";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_SETTINGS = "settings";
    public static final String KEY_USER_AVATAR = "avatar";
    public static final String KEY_SETTING_WORK = "work";
    public static final String KEY_AVATAR_URL = "url";

    public static final String ERROR_TAG_JSON_EXCEPTION = "JSONException";
    public static final String ERROR_TAG_INTERRUPTED_EXCEPTION = "InterruptedException";
    public static final String ERROR_TAG_IO_EXCEPTION = "IOException";


    private Helper() {}

    public JSONObject initJSON(String way) {
        try {
            InputStream is = App.getContext().getAssets().open(way);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, StandardCharsets.UTF_8));
        } catch (IOException ex) {
            Log.e(ERROR_TAG_IO_EXCEPTION,ex.getMessage());
        } catch (JSONException e) {
            Log.e(ERROR_TAG_JSON_EXCEPTION,e.getMessage());
        }
        return null;
    }

    public void imageLoader(ImageView image, String url) {
        Glide.with(App.getContext())
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(RequestOptions.circleCropTransform())
                .into(image);
    }
}
