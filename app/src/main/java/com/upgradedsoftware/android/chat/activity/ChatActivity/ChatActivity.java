package com.upgradedsoftware.android.chat.activity.ChatActivity;

import android.annotation.SuppressLint;
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
import java.util.UUID;

import static com.upgradedsoftware.android.chat.activity.ContactListActivity.ContactListActivity.BUNDLE_CHAT_ID;
import static com.upgradedsoftware.android.chat.activity.ContactListActivity.ContactListActivity.BUNDLE_NAME;
import static com.upgradedsoftware.android.chat.activity.ContactListActivity.ContactListActivity.BUNDLE_URL;

interface ChatActivityInterface {
    String getChatId();
    void newDataReceived(JSONObject object) throws JSONException;
}

public class ChatActivity extends AppCompatActivity implements ChatActivityInterface {

    private static final int ACTIVITY_LAYOUT = R.layout.activity_chat;

    private ChatAdapter adapter;
    private RecyclerView recyclerView;
    private FakeChatRequest serverChat;
    private String mChatId;

    @Override
    public String getChatId() {
        return mChatId;
    }

    @Override
    public void newDataReceived(JSONObject object) throws JSONException {
        DataHolderApp.getInstance().setMessageList(MessageMapper.mapToUI(object), mChatId);
        adapter.setNewData(DataHolderApp.getInstance().getMessageList(mChatId));
        this.recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ACTIVITY_LAYOUT);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDataAndUI();
        initFakeRequests();
        initRecycler();
        initClickListener();
    }

    @Override
    protected void onStop() {
        serverChat.cancel(true);
        DataHolderServer.saveMessagesOnServer(mChatId);
        super.onStop();
    }

    private void initDataAndUI() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            TextView view = findViewById(R.id.userName);
            view.setText(bundle.getString(BUNDLE_NAME));
            ImageView imageView = findViewById(R.id.userAvatar);
            Helper.getInstance().imageLoader(imageView, bundle.getString(BUNDLE_URL));
            mChatId = bundle.getString(BUNDLE_CHAT_ID);
        }
    }

    private void initFakeRequests() {
        serverChat = new FakeChatRequest();
        serverChat.setActivity(this);
        serverChat.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                DataHolderServer.getInstance().getMessagesFormChat(mChatId));
    }

    private void initRecycler() {
        this.recyclerView = findViewById(R.id.recyclerChat);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter(DataHolderApp.getInstance().getMessageList(mChatId));
    }

    private void setAdapter(List<ChatUiModel> data) {
        adapter = new ChatAdapter(data);
        recyclerView.setAdapter(adapter);
        this.recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initClickListener() {
        ImageView imageArrow = findViewById(R.id.arrowBack);
        ImageView imageSendMessage = findViewById(R.id.sendButton);
        EditText editText = findViewById(R.id.messageEntry);
        imageArrow.setOnClickListener(setClickToImageArrow());
        editText.setOnTouchListener(setOnTouchEditText());
        imageSendMessage.setOnClickListener(setSendMessageClick());
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
                        messageRequestModel.getMessageID(),
                        true,
                        messageRequestModel.getTextMessage(),
                        messageRequestModel.getCreated(),
                        MessageStatus.MESSAGE_SENDED
                )
        );
        adapter.setNewData(DataHolderApp.getInstance().getMessageList(mChatId));
        this.recyclerView.smoothScrollToPosition(adapter.getItemCount());
    }

    private View.OnClickListener setSendMessageClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.messageEntry);
                if (!editText.getText().toString().equals("")) {
                    MessageRequestModel message = new MessageRequestModel(
                            UUID.randomUUID().toString(),
                            editText.getText().toString(),
                            TimeParser.getCurrentTime()
                    );
                    sendMessageToServer(message);
                    editText.setText("");
                }
            }
        };
    }

    private View.OnTouchListener setOnTouchEditText() {
        return new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                    }
                }, 100);
                return false;
            }
        };
    }

    private View.OnClickListener setClickToImageArrow() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
    }
}
