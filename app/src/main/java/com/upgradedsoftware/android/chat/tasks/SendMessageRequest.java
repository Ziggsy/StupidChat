package com.upgradedsoftware.android.chat.tasks;

import android.os.AsyncTask;

import com.upgradedsoftware.android.chat.activity.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.models.MessageRequestModel;
import com.upgradedsoftware.android.chat.data.DataHolderServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.UUID;

import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_CREATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_FROM_ME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_MESSAGES;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_TEXT;

public class SendMessageRequest extends AsyncTask<MessageRequestModel, Void, Boolean> {

    private WeakReference<ChatActivity> mActivity;

    @Override
    protected Boolean doInBackground(MessageRequestModel... data) {
        try {
            saveToServerBD(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void saveToServerBD(MessageRequestModel[] data) throws JSONException {
        JSONObject message = new JSONObject();
        message.put(KEY_MESSAGE_ID, UUID.randomUUID().toString());
        message.put(KEY_MESSAGE_FROM_ME, true);
        message.put(KEY_MESSAGE_TEXT, data[0].getTextMessage());
        message.put(KEY_MESSAGE_CREATED, data[0].getCreated());
        JSONObject newData = DataHolderServer.getInstance().mJSONObjectMessages;
        JSONArray messageArray = newData.getJSONArray(KEY_MESSAGE_MESSAGES);
        messageArray.put(message);
        DataHolderServer.getInstance().mJSONObjectMessages = new JSONObject().put(KEY_MESSAGE_MESSAGES, messageArray);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
    }

    public void setActivity(ChatActivity activity) {
        this.mActivity = new WeakReference<>(activity);
    }
}