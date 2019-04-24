package com.upgradedsoftware.android.chat.task;

import android.os.AsyncTask;

import com.upgradedsoftware.android.chat.activity.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.mappers.MessageMapper;
import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.utils.DataHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class FakeChatRequest extends AsyncTask<JSONObject, JSONObject, Void> {

    private ChatActivity mActivity;

    @Override
    protected Void doInBackground(JSONObject... data) {
        try {
            for (int i = 0; i < 9999; i++) {
                publishProgress(data);
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
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

}
