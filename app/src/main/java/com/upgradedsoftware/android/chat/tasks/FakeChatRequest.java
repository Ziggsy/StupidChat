package com.upgradedsoftware.android.chat.tasks;

import android.os.AsyncTask;

import com.upgradedsoftware.android.chat.activity.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.data.DataHolderServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class FakeChatRequest extends AsyncTask<JSONObject, JSONObject, Void> {

    private WeakReference<ChatActivity> mActivity;

    @Override
    protected Void doInBackground(JSONObject... data) {
        try {
            for (int i = 0; i < 9999; i++) {
                publishProgress(DataHolderServer.getInstance().getMessagesFormChat(mActivity.get().getChatId()));
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(JSONObject... values) {
        DataHolderServer.getInstance().saveMessages(mActivity.get().getChatId(),values[0]);
        onReceive(values[0]);
        super.onProgressUpdate(values);
    }

    private void onReceive(JSONObject json) {
        try {
            mActivity.get().newDataReceived(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setActivity(ChatActivity activity) {
        this.mActivity = new WeakReference<>(activity);
    }

}
