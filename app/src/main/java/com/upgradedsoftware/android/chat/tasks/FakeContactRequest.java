package com.upgradedsoftware.android.chat.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.upgradedsoftware.android.chat.activity.ContactListActivity.ContactListActivity;
import com.upgradedsoftware.android.chat.data.DataHolderServer;
import com.upgradedsoftware.android.chat.utils.Helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.upgradedsoftware.android.chat.utils.Helper.ERROR_TAG_INTERRUPTED_EXCEPTION;
import static com.upgradedsoftware.android.chat.utils.Helper.ERROR_TAG_JSON_EXCEPTION;
import static com.upgradedsoftware.android.chat.utils.Helper.JSON_NEW_CHATTERS;
import static com.upgradedsoftware.android.chat.utils.Helper.JSON_SERVER_RESPONSE;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_CHATS;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_LAST_MESSAGE;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UNREAD;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_UPDATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_CREATED;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_FROM_ME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_MESSAGES;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_MESSAGE_TEXT;
import static com.upgradedsoftware.android.chat.utils.Helper.SERVER_FAKE_LATENCY;

public class FakeContactRequest extends AsyncTask<Void, JSONObject, Void> {

    private WeakReference<ContactListActivity> mActivity;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            for (DataHolderServer.getInstance().getCounter(); DataHolderServer.getInstance().getCounter() < 100; DataHolderServer.getInstance().setCounter()) {
                TimeUnit.SECONDS.sleep(SERVER_FAKE_LATENCY);
                JSONObject contactData = getContactData();
                JSONObject newData = randomEvent(contactData, DataHolderServer.getInstance().getCounter());
                newData = setUnreadStatusAndLastMessage(newData);
                publishProgress(newData);
            }
        } catch (InterruptedException ex) {
            Log.e(ERROR_TAG_INTERRUPTED_EXCEPTION, ex.getMessage());
            Thread.currentThread().interrupt();
        } catch (JSONException e) {
            Log.e(ERROR_TAG_JSON_EXCEPTION, e.getMessage());
        }
        return null;
    }

    private JSONObject getContactData() {
        if (DataHolderServer.getInstance().getJSONObjectContact() == null) {
            DataHolderServer.getInstance().setJSONObjectContact(Helper.getInstance().initJSON(JSON_SERVER_RESPONSE));
        }
        return DataHolderServer.getInstance().getJSONObjectContact();
    }


    @Override
    protected void onProgressUpdate(JSONObject... values) {
        DataHolderServer.getInstance().setJSONObjectContact(values[0]);
        onReceive(values[0]);
        super.onProgressUpdate(values);
    }

    private void onReceive(JSONObject json) {
        mActivity.get().newDataReceived(json);
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
                return generateNewChat(newData, counter);
        }
        return null;
    }

    private JSONObject setUnreadStatusAndLastMessage(JSONObject contactData) throws JSONException {
        JSONArray chatsArray = contactData.getJSONArray(KEY_CHAT_CHATS);
        JSONArray newChatsArray = new JSONArray();
        for (int i = 0; i < chatsArray.length(); i++) {
            //Достаём объект чата
            JSONObject chatObject = (JSONObject) chatsArray.get(i);
            String chatID = (String) chatObject.get(KEY_CHAT_ID);
            //Достаём чат
            JSONArray messagesObject = DataHolderServer.getInstance().getMessagesFormChat(chatID).getJSONArray(KEY_MESSAGE_MESSAGES);
            //Достаём последнюю месагу
            JSONObject lastMessageInChat = (JSONObject) messagesObject.get(messagesObject.length() - 1);
            // Меняем поля в JSON'e контакта
            // Будем считать, если последнее сообщение не от нас, то оно не прочитано
            chatObject.put(KEY_CHAT_UNREAD, !lastMessageInChat.get(KEY_MESSAGE_FROM_ME).equals(true));
            chatObject.put(KEY_CHAT_LAST_MESSAGE, lastMessageInChat.get(KEY_MESSAGE_TEXT));
            chatObject.put(KEY_CHAT_UPDATED, lastMessageInChat.getLong(KEY_MESSAGE_CREATED));
            newChatsArray.put(chatObject);
        }
        return new JSONObject().put(KEY_CHAT_CHATS, newChatsArray);
    }

    private JSONObject generateNewChat(JSONObject newData, int counter) throws JSONException {
        JSONObject newChat = Helper.getInstance().initJSON(JSON_NEW_CHATTERS);
        JSONArray chatsArray = newChat.getJSONArray(KEY_CHAT_CHATS);
        if (counter < chatsArray.length()) {
            JSONObject jsonObject = chatsArray.getJSONObject(counter);
            JSONArray newChatArray = newData.getJSONArray(KEY_CHAT_CHATS).put(jsonObject);
            return new JSONObject().put(KEY_CHAT_CHATS, newChatArray);
        } else {
            return newData;
        }
    }

    private int getRandomValue(int value) {
        return new Random().nextInt(value);
    }
}
