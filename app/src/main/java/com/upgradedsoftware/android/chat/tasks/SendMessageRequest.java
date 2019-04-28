package com.upgradedsoftware.android.chat.tasks;

import android.os.AsyncTask;

import com.upgradedsoftware.android.chat.models.MessageRequestModel;

import java.util.concurrent.TimeUnit;

public class SendMessageRequest extends AsyncTask<MessageRequestModel, Void, Void> {

    @Override
    protected Void doInBackground(MessageRequestModel... data) {
        try {


            TimeUnit.SECONDS.sleep(3);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}