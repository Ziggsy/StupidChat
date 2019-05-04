package com.upgradedsoftware.android.chat.activity.ChatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.adapters.ChatAdapter;
import com.upgradedsoftware.android.chat.data.DataHolderApp;
import com.upgradedsoftware.android.chat.data.DataHolderServer;
import com.upgradedsoftware.android.chat.mappers.MessageMapper;
import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.MessageRequestModel;
import com.upgradedsoftware.android.chat.models.MessageStatus;
import com.upgradedsoftware.android.chat.tasks.FakeChatRequest;
import com.upgradedsoftware.android.chat.tasks.SendMessageRequest;
import com.upgradedsoftware.android.chat.utils.Helper;
import com.upgradedsoftware.android.chat.utils.TimeParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

interface ChatActivityInterface {
    String getChatId();
    void newDataReceived(JSONObject object) throws JSONException;
}

public class ChatActivity extends AppCompatActivity implements ChatActivityInterface {

    private ChatAdapter adapter;
    private boolean firstSetup = true;
    private RecyclerView recyclerView;
    private FakeChatRequest serverChat;
    private String mChatId;

    @Override
    public String getChatId(){
        return mChatId;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initDataAndUI();
        initClickListener();
        try {
            initMessageList();
            initFakeRequests();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initDataAndUI() {
        Bundle bundle = getIntent().getExtras();
        TextView view = findViewById(R.id.userName);
        if (bundle != null) {
            view.setText(bundle.getString("name"));
            ImageView imageView = findViewById(R.id.userAvatar);
            Helper.getInstance().imageLoader(imageView, bundle.getString("url"));
            mChatId = bundle.getString("chatID");
        }
    }

    private void initClickListener() {
        ImageView image = findViewById(R.id.arrowBack);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView imageSendMessage = findViewById(R.id.sendButton);
        EditText editText = findViewById(R.id.messageEntry);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }
                }, 200);
                return false;
            }
        });

        imageSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.messageEntry);
                if (!editText.getText().toString().equals("")) {
                    MessageRequestModel message = new MessageRequestModel(
                            mChatId,
                            editText.getText().toString(),
                            TimeParser.getCurrentTime()
                    );
                    sendMessageToServer(message);
                    editText.setText("");
                }
            }
        });
    }

    private void sendMessageToServer(MessageRequestModel messageRequestModel) {
        SendMessageRequest messageRequest = new SendMessageRequest();
        messageRequest.setActivity(this);
        messageRequest.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, messageRequestModel);
        cacheMessage(messageRequestModel);
    }

    private void cacheMessage(MessageRequestModel messageRequestModel) {
        adapter.addToCache(
                new ChatUiModel(
                        "",
                        true,
                        messageRequestModel.getTextMessage(),
                        messageRequestModel.getCreated(),
                        MessageStatus.MESSAGE_CACHED
                )
        );
        updateAdapterData();
    }

    private void initMessageList() throws JSONException {
        DataHolderServer.getInstance().saveMessages(mChatId, Helper.getInstance().initJSON(mChatId));
    }

    private void initFakeRequests() throws JSONException {
        serverChat = new FakeChatRequest();
        serverChat.setActivity(this);
        serverChat.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DataHolderServer.getInstance().getMessagesFormChat(mChatId));
    }

    private void initRecycler(List<ChatUiModel> data) {
        this.recyclerView = findViewById(R.id.recyclerChat);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(data);
        recyclerView.setAdapter(adapter);
        this.recyclerView.smoothScrollToPosition(adapter.getItemCount());
        firstSetup = false;
    }

    @Override
    public void newDataReceived(JSONObject object) throws JSONException {
        DataHolderApp.getInstance().setMessageList(MessageMapper.mapToUI(object), mChatId);
        updateAdapterData();
    }

    private void updateAdapterData() {
        if (firstSetup) {
            initRecycler(DataHolderApp.getInstance().getMessageList(mChatId));
        } else {
            adapter.setNewData(DataHolderApp.getInstance().getMessageList(mChatId));
            adapter.notifyDataSetChanged();
            this.recyclerView.smoothScrollToPosition(adapter.getItemCount());
        }
    }

    @Override
    protected void onStop() {
        serverChat.cancel(true);
        try {
            DataHolderServer.saveMessagesOnServer(mChatId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onStop();
    }
}
