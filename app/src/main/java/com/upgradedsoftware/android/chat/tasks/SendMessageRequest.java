package com.upgradedsoftware.android.chat.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.upgradedsoftware.android.chat.activity.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.models.MessageRequestModel;
import com.upgradedsoftware.android.chat.data.DataHolderServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import static com.upgradedsoftware.android.chat.utils.Helper.ERROR_TAG_INTERRUPTED_EXCEPTION;
import static com.upgradedsoftware.android.chat.utils.Helper.ERROR_TAG_JSON_EXCEPTION;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_CREATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_FROM_ME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_MESSAGES;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_TEXT;

public class SendMessageRequest extends AsyncTask<MessageRequestModel, Void, Void> {

    private WeakReference<ChatActivity> mActivity;

    @Override
    protected Void doInBackground(MessageRequestModel... data) {
        try {
            TimeUnit.SECONDS.sleep(1);
            saveToServerBD(data);
        } catch (JSONException e) {
            Log.e(ERROR_TAG_JSON_EXCEPTION,e.getMessage());
        } catch (InterruptedException ex){
            Log.e(ERROR_TAG_INTERRUPTED_EXCEPTION,ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void saveToServerBD(MessageRequestModel[] data) throws JSONException {
        JSONObject message = new JSONObject();
        message.put(KEY_MESSAGE_ID, data[0].getMessageID());
        message.put(KEY_MESSAGE_FROM_ME, true);
        message.put(KEY_MESSAGE_TEXT, data[0].getTextMessage());
        message.put(KEY_MESSAGE_CREATED, data[0].getCreated());
        JSONObject newData = DataHolderServer.getInstance().getMessagesFormChat(mActivity.get().getChatId());
        JSONArray messageArray = newData.getJSONArray(KEY_MESSAGE_MESSAGES);
        messageArray.put(message);
        DataHolderServer.getInstance().saveMessages(mActivity.get().getChatId(),new JSONObject().put(KEY_MESSAGE_MESSAGES, messageArray));
    }


    public void setActivity(ChatActivity activity) {
        this.mActivity = new WeakReference<>(activity);
    }
}