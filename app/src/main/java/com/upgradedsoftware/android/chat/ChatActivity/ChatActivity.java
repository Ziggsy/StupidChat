package com.upgradedsoftware.android.chat.ChatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.adapters.ChatAdapter;
import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.task.FakeChatRequest;
import com.upgradedsoftware.android.chat.utils.DataHolder;
import com.upgradedsoftware.android.chat.utils.Helper;

import java.util.ArrayList;
import java.util.List;

interface ChatActivityInterface {

    void newDataReceived(ArrayList<ChatUiModel> object);
}

public class ChatActivity extends AppCompatActivity implements ChatActivityInterface {

    ChatAdapter adapter;
    private boolean first_setup = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initMessageList();
        initFakeRequests();
        initClickListener();
    }

    private void initClickListener() {
        ImageView image = findViewById(R.id.arrowBack);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initMessageList() {
        DataHolder.getInstance().mJSONObjectMessages = Helper.getInstance().initJSON(this, Helper.JSON_CHAT_MESSAGES);
    }

    private void initFakeRequests() {
        FakeChatRequest serverChat = new FakeChatRequest();
        serverChat.setActivity(this);
        serverChat.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DataHolder.getInstance().mJSONObjectMessages);
    }

    private void initRecycler(List<ChatUiModel> data) {
        RecyclerView recyclerView = findViewById(R.id.recyclerChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setClickListener(this);
        adapter = new ChatAdapter(this, data);
        recyclerView.setAdapter(adapter);
        first_setup = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void newDataReceived(ArrayList<ChatUiModel> data) {
        DataHolder.getInstance().mChatUiModel = data;

        if (first_setup) {
            initRecycler(data);
        } else {
            adapter.setNewData(data);
            adapter.notifyDataSetChanged();
            RecyclerView recyclerView = findViewById(R.id.recyclerChat);
            recyclerView.scrollToPosition(adapter.getItemCount());
        }
    }
}
