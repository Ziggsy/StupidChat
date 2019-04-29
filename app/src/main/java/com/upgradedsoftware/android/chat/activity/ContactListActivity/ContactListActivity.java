package com.upgradedsoftware.android.chat.activity.ContactListActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.activity.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.adapters.ContactsAdapter;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.models.UserModelShort;
import com.upgradedsoftware.android.chat.tasks.FakeContactRequest;
import com.upgradedsoftware.android.chat.utils.BottomSheetDialog;
import com.upgradedsoftware.android.chat.utils.DataHolder;
import com.upgradedsoftware.android.chat.utils.Helper;

import java.util.List;

import static com.upgradedsoftware.android.chat.utils.Helper.JSON_SERVER_RESPONSE;
import static com.upgradedsoftware.android.chat.utils.Helper.LIST_STATE_KEY;

interface ContactListInterface {
    void newDataReceived(List<ContactUiModel> data);
}

public class ContactListActivity extends AppCompatActivity implements ContactListInterface {

    private static final Integer ACTIVITY_LAYOUT = R.layout.activity_main;

    private ContactsAdapter adapter;
    private RecyclerView mRecyclerView;
    private boolean first_setup = true;
    private FakeContactRequest server;
    private Parcelable mListState;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ACTIVITY_LAYOUT);
        initChatList();
        initFakeServer();
        initBottomSheet();
        initRecycler();
    }

    private void initChatList() {
        DataHolder.getInstance().mJSONObjectContact = Helper.getInstance().initJSON(this, JSON_SERVER_RESPONSE);
    }

    private void initFakeServer() {
        server = new FakeContactRequest();
        server.setActivity(this);
        server.execute(DataHolder.getInstance().mJSONObjectContact);
    }

    private void initBottomSheet() {
        RelativeLayout header = findViewById(R.id.header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
            }
        });
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.recyclerContacts);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.manager = mRecyclerView.getLayoutManager();
    }

    private void setAdapter(List<ContactUiModel> data) {
        ContactsAdapter.ItemClickListener listener = new ContactsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ContactListActivity.this, ChatActivity.class);
                intent.putExtra("url", adapter.getData().get(position).getUser().getUserAvatars().getUrl());
                intent.putExtra("chatID", adapter.getData().get(position).getChatId());
                intent.putExtra("name", adapter.getData().get(position).getUser().getName());
                ContactListActivity.this.startActivity(intent);
            }
        };

        ContactsAdapter.AvatarClickListener avatarClickListener = new ContactsAdapter.AvatarClickListener() {
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
        adapter = new ContactsAdapter(data, listener, avatarClickListener);
        mRecyclerView.setAdapter(adapter);
        first_setup = false;
    }

    @Override
    public void newDataReceived(List<ContactUiModel> data) {
        DataHolder.getInstance().mContactUiModel = data; // Якобы сохраняю в бд

        final List<ContactUiModel> listChatModel = DataHolder.getInstance().mContactUiModel;

        if (first_setup) {
            setAdapter(data);
        } else {
            adapter.setNewData(data);
            adapter.notifyDataSetChanged();
        }
    }


    public void showBottomSheet(UserModelShort model) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.id = model.getUserInfo();
        bottomSheet.userName = model.getName();
        bottomSheet.userInfo = model.getUserInfo();
        bottomSheet.url = model.getUrl();
        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
    }

    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save list state
        Parcelable mListState = manager.onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
    }

    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        // Retrieve list state and list/item positions
        if (state != null)
            mListState = state.getParcelable(LIST_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tryToRestore();
    }

    private void tryToRestore() {
        if (mListState != null && manager != null) {
            manager.onRestoreInstanceState(mListState);
        }
    }

    @Override
    protected void onStop() {
        server.cancel(true);
        super.onStop();
    }


}
