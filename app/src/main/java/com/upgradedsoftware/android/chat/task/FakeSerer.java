package com.upgradedsoftware.android.chat.task;

import android.os.AsyncTask;

import com.upgradedsoftware.android.chat.mainActivity.ContactListActivity;
import com.upgradedsoftware.android.chat.mappers.ChatListMapper;
import com.upgradedsoftware.android.chat.models.ChatsUiModel;
import com.upgradedsoftware.android.chat.utils.DataHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.upgradedsoftware.android.chat.mappers.ChatListMapper.KEY_CHAT_CHATS;

public class FakeSerer extends AsyncTask<JSONObject, JSONObject, Void> {

    private ContactListActivity mActivity;

    @Override
    protected Void doInBackground(JSONObject... data) {
        try {
            for (int i = 0; i < 9999; i++) {
                TimeUnit.SECONDS.sleep(10);
//                JSONObject newData = randomEvent(data[0]);
                publishProgress(data);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(JSONObject... values) {
        DataHolder.getInstance().mJSONObject = values[0];
        onReceive(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }

    public void onReceive(JSONObject json) {
        ChatListMapper mapper = new ChatListMapper();
        try {
            ArrayList<ChatsUiModel> object = mapper.mapToUI(json);
            mActivity.newDataReceived(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setActivity(ContactListActivity contactListActivity) {
        this.mActivity = contactListActivity;
    }


    private JSONObject randomEvent(JSONObject newData) throws JSONException {
        Random rand = new Random();
        int r = rand.nextInt(10);

        switch (r){
            case 0: return generateNewChat(newData);
        }



        return null;
    }

    private JSONObject generateNewChat(JSONObject newData) throws JSONException {
        JSONObject newChat = new JSONObject();
        newData.put(KEY_CHAT_CHATS, newChat);
        return newData;
    }
}
