package com.upgradedsoftware.android.chat.mainActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.adapters.ContactsAdapter;
import com.upgradedsoftware.android.chat.models.ChatsUiModel;
import com.upgradedsoftware.android.chat.task.FakeSerer;
import com.upgradedsoftware.android.chat.utils.DataHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

interface ContactListInterface {
    void newDataReceived(List<ChatsUiModel> data);
}

public class ContactListActivity extends AppCompatActivity implements ContactListInterface {

    ContactsAdapter adapter;
    private boolean first_setup = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataHolder.getInstance().imageMap = new HashMap<>();
        initChatList();
    }

    private void initChatList() {
        FakeSerer server = new FakeSerer();
        DataHolder.getInstance().mJSONObject = initJSON();
        server.setActivity(this);
        server.execute(DataHolder.getInstance().mJSONObject);
    }

    private void initRecycler(List<ChatsUiModel> data) {
        RecyclerView recyclerView = findViewById(R.id.recyclerContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactsAdapter(this, data);
//        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        first_setup = false;
    }

    @Override
    public void newDataReceived(List<ChatsUiModel> data) {
        DataHolder.getInstance().mChatsUiModel = data;

        final List<ChatsUiModel> listChatModel = DataHolder.getInstance().mChatsUiModel;

        for (int i = 0; i < DataHolder.getInstance().mChatsUiModel.size(); i++) {
            if (!DataHolder.getInstance().imageMap.containsKey(listChatModel.get(i).getUser().getUserId())) {
                final String key = listChatModel.get(i).getUser().getUserId();
                Glide.with(this)
                        .asBitmap()
                        .load(data.get(i).getUser().getUserAvatars().getUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                DataHolder.getInstance().imageMap.put(key, resource);
                            }
                        });
            }
        }


        if (first_setup) {
            initRecycler(data);
        } else {
            adapter.setNewData(data);
            adapter.notifyDataSetChanged();
        }


    }

    private JSONObject initJSON() {
        try {
            InputStream is = getAssets().open("ServerResponse.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer, "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
