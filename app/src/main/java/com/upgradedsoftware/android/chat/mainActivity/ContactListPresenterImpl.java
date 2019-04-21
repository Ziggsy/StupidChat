package com.upgradedsoftware.android.chat.mainActivity;

import com.upgradedsoftware.android.chat.mappers.ChatListMapper;

import org.json.JSONException;
import org.json.JSONObject;

interface ContactListPresenter {
    void onRecieve(JSONObject json);
}

public class ContactListPresenterImpl implements ContactListPresenter {

    @Override
    public void onRecieve(JSONObject json) {
        ChatListMapper mapper = new ChatListMapper();
        try {
            mapper.mapToUI(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
