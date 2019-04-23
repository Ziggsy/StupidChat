package com.upgradedsoftware.android.chat.ChatActivity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.task.FakeChatRequest;
import com.upgradedsoftware.android.chat.utils.DataHolder;
import com.upgradedsoftware.android.chat.utils.Helper;

public class ChatActivity extends AppCompatActivity {

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
        FakeChatRequest server = new FakeChatRequest();
        server.setActivity(this);
        server.execute(DataHolder.getInstance().mJSONObjectMessages);
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
}
