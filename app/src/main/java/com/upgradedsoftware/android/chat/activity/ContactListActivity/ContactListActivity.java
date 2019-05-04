package com.upgradedsoftware.android.chat.activity.ContactListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.activity.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.adapters.ContactsAdapter;
import com.upgradedsoftware.android.chat.data.DataHolderApp;
import com.upgradedsoftware.android.chat.data.DataHolderServer;
import com.upgradedsoftware.android.chat.mappers.ChatListMapper;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.models.UserModelShort;
import com.upgradedsoftware.android.chat.tasks.FakeContactRequest;
import com.upgradedsoftware.android.chat.utils.BottomSheetDialog;
import com.upgradedsoftware.android.chat.utils.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.upgradedsoftware.android.chat.utils.Helper.BOTTOM_SHEET_TAG;
import static com.upgradedsoftware.android.chat.utils.Helper.ERROR_TAG_JSON_EXCEPTION;
import static com.upgradedsoftware.android.chat.utils.Helper.URL_MY_AVATAR;

interface ContactListInterface {
    void newDataReceived(JSONObject data);
}

public class ContactListActivity extends AppCompatActivity implements ContactListInterface {

    private static final int ACTIVITY_LAYOUT = R.layout.activity_main;

    public static class Key {
        private Key() {
        }

        public static final String BUNDLE_NAME = "bundle.name";
        public static final String BUNDLE_URL = "bundle.url";
        public static final String BUNDLE_CHAT_ID = "bundle.chat.id";
    }

    private ContactsAdapter adapter;
    private RecyclerView mRecyclerView;
    private FakeContactRequest server;

    @Override
    public void newDataReceived(JSONObject data) {
        try {
            List<ContactUiModel> dataUI = ChatListMapper.mapToUI(data);
            DataHolderApp.getInstance().setContactUiModel(dataUI); // Якобы сохраняю в бд
            adapter.setNewData(DataHolderApp.getInstance().getContactUiModel());
        } catch (JSONException e) {
            Log.e(ERROR_TAG_JSON_EXCEPTION, e.getMessage());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ACTIVITY_LAYOUT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initFakeServer();
        initBottomSheet();
        initMyAvatar();
        initRecycler();
    }

    @Override
    protected void onStop() {
        server.cancel(true);
        try {
            // Активити не должна трогать DataHolderServer, нужна прослойка
            DataHolderServer.saveToServerModel();
        } catch (JSONException e) {
            Log.e(ERROR_TAG_JSON_EXCEPTION, e.getMessage());
        }
        super.onStop();
    }


    private void initFakeServer() {
        server = new FakeContactRequest();
        server.setActivity(this);
        server.execute();
    }

    private void initBottomSheet() {
        RelativeLayout header = findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), BOTTOM_SHEET_TAG);
            }
        });
    }

    private void initMyAvatar() {
        ImageView myAvatar = findViewById(R.id.menu);
        Helper.getInstance().imageLoader(myAvatar, URL_MY_AVATAR);
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.recyclerContacts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapter(DataHolderApp.getInstance().getContactUiModel());
    }

    private void setAdapter(List<ContactUiModel> data) {
        ContactsAdapter.ItemClickListener listener = getItemClickListener();
        ContactsAdapter.AvatarClickListener avatarClickListener = getAvatarClickListener();
        adapter = new ContactsAdapter(data, listener, avatarClickListener);
        mRecyclerView.setAdapter(adapter);
    }

    private ContactsAdapter.AvatarClickListener getAvatarClickListener() {
        return new ContactsAdapter.AvatarClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showBottomSheet(new UserModelShort(
                                adapter.getData().get(position).getUser().getUserId(),
                                adapter.getData().get(position).getUser().getName(),
                                adapter.getData().get(position).getUser().getSettings().getWork(),
                                adapter.getData().get(position).getUser().getUserAvatars().getUrl()
                        )
                );
            }
        };
    }

    private ContactsAdapter.ItemClickListener getItemClickListener() {
        return new ContactsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ContactListActivity.this, ChatActivity.class);
                intent.putExtra(Key.BUNDLE_URL, adapter.getData().get(position).getUser().getUserAvatars().getUrl());
                intent.putExtra(Key.BUNDLE_CHAT_ID, adapter.getData().get(position).getChatId());
                intent.putExtra(Key.BUNDLE_NAME, adapter.getData().get(position).getUser().getName());
                ContactListActivity.this.startActivity(intent);
            }
        };
    }


    private void showBottomSheet(UserModelShort model) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.setId(model.getUserInfo());
        bottomSheet.setUserName(model.getName());
        bottomSheet.setUserInfo(model.getUserInfo());
        bottomSheet.setUrl(model.getUrl());
        bottomSheet.show(getSupportFragmentManager(), BOTTOM_SHEET_TAG);
    }

}
