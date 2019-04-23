package com.upgradedsoftware.android.chat.task;

import android.os.AsyncTask;

import com.upgradedsoftware.android.chat.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.ContactList.ContactListActivity;
import com.upgradedsoftware.android.chat.mappers.ChatListMapper;
import com.upgradedsoftware.android.chat.mappers.MessageMapper;
import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.utils.DataHolder;
import com.upgradedsoftware.android.chat.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.upgradedsoftware.android.chat.utils.Helper.JSON_NEW_CHATTERS;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CHATS;

public class FakeChatRequest extends AsyncTask<JSONObject, JSONObject, Void> {

    private ChatActivity mActivity;

    @Override
    protected Void doInBackground(JSONObject... data) {
        try {
            for (int i = 0; i < 9999; i++) {
                TimeUnit.SECONDS.sleep(5);
                JSONObject newData = randomEvent(data[0]);
                publishProgress(newData);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(JSONObject... values) {
        DataHolder.getInstance().mJSONObjectContact = values[0];
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
        MessageMapper mapper = new MessageMapper();

        try {
            ArrayList<ChatUiModel> object = mapper.mapToUI(json);
            mActivity.newDataReceived(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setActivity(ChatActivity activity) {
        this.mActivity = activity;
    }


    private JSONObject randomEvent(JSONObject newData) throws JSONException, IOException {
        switch (getRandomValue(1)) {
            case 0:
                return newData;
            case 1:
                return newData;
            case 2:
                return newData;
            case 3:
                return generateMessage(newData);
            case 4:
                return generateMessage(newData);
            case 5:
                return newData;
            case 6:
                return newData;
            case 7:
                return newData;
            case 8:
                return newData;
            case 9:
                return newData;
        }
        return null;
    }

    private JSONObject generateMessage(JSONObject newData) throws JSONException {
        JSONObject newChat = Helper.getInstance().initJSON(mActivity, JSON_NEW_CHATTERS);
        JSONArray chatsArray = newChat.getJSONArray(KEY_CHAT_CHATS);
        JSONObject jsonObject = chatsArray.getJSONObject(getRandomValue(chatsArray.length()));
        JSONArray newChatArray = newData.getJSONArray(KEY_CHAT_CHATS).put(jsonObject);
        return new JSONObject().put(KEY_CHAT_CHATS, newChatArray);
    }

    private int getRandomValue(Integer value) {
        return new Random().nextInt(value);
    }
}
