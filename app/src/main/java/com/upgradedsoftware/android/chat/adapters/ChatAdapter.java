package com.upgradedsoftware.android.chat.adapters;

import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.data.DataHolderApp;
import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ItemViewType;
import com.upgradedsoftware.android.chat.models.MessageStatus;
import com.upgradedsoftware.android.chat.utils.ChatDiff;
import com.upgradedsoftware.android.chat.utils.TimeParser;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatUiModel> mData;
    private List<ChatUiModel> cacheData = new ArrayList<>();

    public void addToCache(ChatUiModel element) {
        cacheData.add(element);
    }

    public void setNewData(List<ChatUiModel> data) {
        List<ChatUiModel> temp = new ArrayList<>(data);
        if(!cacheData.isEmpty()){
            for (int i = 0; i < data.size(); i++){
                for (int j = 0; j < cacheData.size(); j++) {
                    if(data.get(i).getTextMessage().equals(cacheData.get(j).getTextMessage()) && !data.get(i).getMessageStatus().equals(cacheData.get(j).getMessageStatus())){
                        cacheData.remove(j);
                        temp.get(i).setMessageStatus(MessageStatus.MESSAGE_CACHED_SAVED);
                    }
                }
            }
            temp.addAll(cacheData);
        }
        this.mData = temp;
    }

    public ChatAdapter(List<ChatUiModel> data) {
        this.mData = data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater;
        switch (viewType) {
            case ItemViewType.FOR: {
                mInflater = LayoutInflater.from(viewGroup.getContext());
                View view = mInflater.inflate((R.layout.item_message_for_user), viewGroup, false);
                return new ViewHolderForMe(view);
            }
            case ItemViewType.FROM: {
                mInflater = LayoutInflater.from(viewGroup.getContext());
                View view = mInflater.inflate((R.layout.item_message_from_user), viewGroup, false);
                return new ViewHolderFromMe(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ItemViewType.FOR: {
                ViewHolderForMe viewHolderForMe = (ViewHolderForMe) viewHolder;
                viewHolderForMe.bind(mData.get(position));
                break;
            }
            case ItemViewType.FROM: {
                ViewHolderFromMe viewHolderFromMe = (ViewHolderFromMe) viewHolder;
                viewHolderFromMe.bind(mData.get(position));
                break;
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getFromMe()) return ItemViewType.FROM;
        else return ItemViewType.FOR;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ViewHolderForMe extends RecyclerView.ViewHolder {

        private TextView message;
        private TextView time;

        ViewHolderForMe(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }

        void bind(ChatUiModel item) {
            message.setText(item.getTextMessage());
            time.setText(TimeParser.parseInDay(item.getCreated()));
        }

    }

    private class ViewHolderFromMe extends RecyclerView.ViewHolder {

        private TextView message;
        private TextView time;
        private RelativeLayout root;
        private ProgressBar progressBar;

        ViewHolderFromMe(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            root = itemView.findViewById(R.id.root);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        void bind(ChatUiModel item) {
            message.setText(item.getTextMessage());
            time.setText(TimeParser.parseInDay(item.getCreated()));

            time.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            root.setBackgroundResource(R.drawable.rounded_corner_from_you);

            switch (item.getMessageStatus()){
                case MessageStatus.MESSAGE_CACHED: {
                    time.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);
                    root.setBackgroundResource(R.drawable.rounded_corner_from_you_cached_saved);
                    return;
                }
                case MessageStatus.MESSAGE_CACHED_SAVED:{
                    progressBar.setVisibility(View.GONE);
                    time.setVisibility(View.VISIBLE);

                    AnimationDrawable anim;
                    root.setBackgroundResource(R.drawable.animation_message);
                    anim = (AnimationDrawable) root.getBackground();
                    anim.setEnterFadeDuration(250);
                    anim.setExitFadeDuration(100000);
                    anim.start();


                    item.setMessageStatus(MessageStatus.MESSAGE_OK);
                    return;
                }
                default:{
                    time.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    root.setBackgroundResource(R.drawable.rounded_corner_from_you);
                }
            }
        }

    }
}
