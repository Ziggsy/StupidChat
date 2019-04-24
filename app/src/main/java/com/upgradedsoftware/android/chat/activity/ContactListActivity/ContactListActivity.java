package com.upgradedsoftware.android.chat.activity.ContactListActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.activity.ChatActivity.ChatActivity;
import com.upgradedsoftware.android.chat.adapters.ContactsAdapter;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.models.UserModelShort;
import com.upgradedsoftware.android.chat.task.FakeContactRequest;
import com.upgradedsoftware.android.chat.utils.DataHolder;
import com.upgradedsoftware.android.chat.utils.BottomSheetDialog;
import com.upgradedsoftware.android.chat.utils.Helper;

import java.util.HashMap;
import java.util.List;

import static com.upgradedsoftware.android.chat.utils.Helper.JSON_SERVER_RESPONSE;

interface ContactListInterface {
    void newDataReceived(List<ContactUiModel> data);

    void showBottomSheet(UserModelShort model);
}

public class ContactListActivity extends AppCompatActivity implements ContactListInterface {

    ContactsAdapter adapter;
    private boolean first_setup = true;
    private static final Integer ACTIVITY_LAYOUT = R.layout.activity_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ACTIVITY_LAYOUT);
        initChatList();
        initFakeServer();
        initBottomSheet();
    }


    private void initChatList() {
        DataHolder.getInstance().imageMap = new HashMap<>();
        DataHolder.getInstance().mJSONObjectContact = Helper.getInstance().initJSON(this, JSON_SERVER_RESPONSE);
    }

    private void initFakeServer() {
        FakeContactRequest server = new FakeContactRequest();
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

    private void initRecycler(List<ContactUiModel> data) {
        RecyclerView recyclerView = findViewById(R.id.recyclerContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ContactsAdapter.ItemClickListener listener = new ContactsAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ContactListActivity.this, ChatActivity.class);
                intent.putExtra("key", adapter.getData().get(position).getUser().getUserId());
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
                                adapter.getData().get(position).getUser().getSettings().getWork()
                        )
                );
            }
        };

        adapter = new ContactsAdapter(this, data, listener, avatarClickListener);
        recyclerView.setAdapter(adapter);
        first_setup = false;
    }

    @Override
    public void newDataReceived(List<ContactUiModel> data) {
        DataHolder.getInstance().mContactUiModel = data; // Якобы сохраняю в бд

        final List<ContactUiModel> listChatModel = DataHolder.getInstance().mContactUiModel;

        for (int i = 0; i < DataHolder.getInstance().mContactUiModel.size(); i++) { // Проверяю есть ли уже аватарка у пользователя
            if (!DataHolder.getInstance().imageMap.containsKey(listChatModel.get(i).getUser().getUserId())) {
                downloadImage(data, listChatModel, i);
            }
        }

        if (first_setup) {
            initRecycler(data);
        } else {
            adapter.setNewData(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showBottomSheet(UserModelShort model) {
        BottomSheetDialog bottomSheet = new BottomSheetDialog();
        bottomSheet.id = model.getUserId();
        bottomSheet.userName = model.getName();
        bottomSheet.userInfo = model.getUserInfo();
        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
    }

    private void downloadImage(List<ContactUiModel> data, List<ContactUiModel> listChatModel, int i) {
        final String key = listChatModel.get(i).getUser().getUserId();
        Glide.with(this)
                .asBitmap()
                .load(data.get(i).getUser().getUserAvatars().getUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        DataHolder.getInstance().imageMap.put(key, resource);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}
