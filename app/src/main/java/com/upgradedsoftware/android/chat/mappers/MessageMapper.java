package com.upgradedsoftware.android.chat.mappers;

import com.upgradedsoftware.android.chat.models.ChatUiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_CREATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_FROM_ME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_MESSAGES;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_TEXT;

public class MessageMapper {

    public ArrayList<ChatUiModel> mapToUI(JSONObject response) throws JSONException {

        ArrayList<ChatUiModel> list = new ArrayList<>();

        JSONArray chatsArray = response.getJSONArray(KEY_MESSAGE_MESSAGES);

        for (int i = 0; i < chatsArray.length(); i++) {
            JSONObject jsonElement = chatsArray.getJSONObject(i);
            ChatUiModel element = new ChatUiModel(
                    jsonElement.getString(KEY_MESSAGE_ID),
                    jsonElement.getBoolean(KEY_MESSAGE_FROM_ME),
                    jsonElement.getString(KEY_MESSAGE_TEXT),
                    jsonElement.getLong(KEY_MESSAGE_CREATED));
            list.add(element);
        }

        return list;
    }
}
