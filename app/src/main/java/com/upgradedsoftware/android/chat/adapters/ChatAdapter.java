package com.upgradedsoftware.android.chat.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.utils.TimeParser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatUiModel> mData;
    private LayoutInflater mInflater;

    public void setNewData(List<ChatUiModel> data) {
        this.mData = data;
    }

    public ChatAdapter(Context context, List<ChatUiModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 0: {
                View view = mInflater.inflate((R.layout.item_message_for_user), viewGroup, false);
                return new ViewHolderForMe(view);
            }
            case 1: {
                View view = mInflater.inflate((R.layout.item_message_from_user), viewGroup, false);
                return new ViewHolderFromMe(view);
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case 0: {
                ViewHolderForMe viewHolderForMe = (ViewHolderForMe) viewHolder;
                viewHolderForMe.bind(mData.get(position));
                break;
            }
            case 1:  {
                ViewHolderFromMe viewHolderFromMe = (ViewHolderFromMe) viewHolder;
                viewHolderFromMe.bind(mData.get(position));
                break;
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getFromMe()) return 1;
        else return 0;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolderForMe extends RecyclerView.ViewHolder {

        TextView message;
        TextView time;

        ViewHolderForMe(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }

        void bind(ChatUiModel item) {
            message.setText(item.getTextMessage());
            time.setText(new TimeParser().parseInDay(item.getCreated()));
        }

    }

    public class ViewHolderFromMe extends RecyclerView.ViewHolder {

        TextView message;
        TextView time;

        ViewHolderFromMe(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }

        void bind(ChatUiModel item) {
            message.setText(item.getTextMessage());
            time.setText(new TimeParser().parseInDay(item.getCreated()));
        }

    }
}
