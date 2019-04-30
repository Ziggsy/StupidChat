package com.upgradedsoftware.android.chat.tasks;

import android.os.AsyncTask;

import com.upgradedsoftware.android.chat.activity.ContactListActivity.ContactListActivity;
import com.upgradedsoftware.android.chat.mappers.ChatListMapper;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.utils.DataHolder;
import com.upgradedsoftware.android.chat.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.upgradedsoftware.android.chat.utils.Helper.JSON_NEW_CHATTERS;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CHATS;

public class FakeContactRequest extends AsyncTask<JSONObject, JSONObject, Void> {

    private WeakReference<ContactListActivity> mActivity;

    @Override
    protected Void doInBackground(JSONObject... data) {
        try {
            for (DataHolder.getInstance().getCounter(); DataHolder.getInstance().getCounter() < 100; DataHolder.getInstance().setCounter()) {
                TimeUnit.SECONDS.sleep(5);
                JSONObject newData = randomEvent(data[0], DataHolder.getInstance().getCounter());
                publishProgress(newData);
            }
        } catch (InterruptedException e) {
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

    private void onReceive(JSONObject json) {
        try {
            ArrayList<ContactUiModel> object = ChatListMapper.mapToUI(json);
            mActivity.get().newDataReceived(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setActivity(ContactListActivity contactListActivity) {
        this.mActivity = new WeakReference<>(contactListActivity);
    }


    private JSONObject randomEvent(JSONObject newData, int counter) throws JSONException {
        switch (getRandomValue(10)) {
            // Тут хотел сделать другие ивенты, к примеру новый unread, новая ава и т.д
            case 0:
                return generateNewChat(newData, counter);
            case 1:
                return generateNewChat(newData, counter);
            case 2:
                return generateNewChat(newData, counter);
            case 3:
                return generateNewChat(newData, counter);
            case 4:
                return generateNewChat(newData, counter);
            case 5:
                return generateNewChat(newData, counter);
            case 6:
                return generateNewChat(newData, counter);
            case 7:
                return generateNewChat(newData, counter);
            case 8:
                return generateNewChat(newData, counter);
            case 9:
                return newData;
        }
        return null;
    }

    private JSONObject generateNewChat(JSONObject newData, int counter) throws JSONException {
        JSONObject newChat = Helper.getInstance().initJSON(mActivity.get(), JSON_NEW_CHATTERS);
        JSONArray chatsArray = newChat.getJSONArray(KEY_CHAT_CHATS);
        if (counter < chatsArray.length()) {
            JSONObject jsonObject = chatsArray.getJSONObject(counter);
            JSONArray newChatArray = newData.getJSONArray(KEY_CHAT_CHATS).put(jsonObject);
            return new JSONObject().put(KEY_CHAT_CHATS, newChatArray);
        } else {
            return newData;
        }
    }

    private int getRandomValue(Integer value) {
        return new Random().nextInt(value);
    }
}
