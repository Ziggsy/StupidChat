package com.upgradedsoftware.android.chat.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.utils.Helper;
import com.upgradedsoftware.android.chat.utils.MyDiffCallback;
import com.upgradedsoftware.android.chat.utils.TimeParser;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<ContactUiModel> mData;
    private ItemClickListener mClickListener;
    private AvatarClickListener mAvatarListener;

    public ContactsAdapter(List<ContactUiModel> data, ItemClickListener listener, AvatarClickListener mAvatarListener) {
        this.mData = data;
        this.mClickListener = listener;
        this.mAvatarListener = mAvatarListener;
    }

    public List<ContactUiModel> getData() {
        return mData;
    }

    public void setNewData(List<ContactUiModel> data) {
        final MyDiffCallback diffCallback = new MyDiffCallback(data, this.mData);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.mData.clear();
        this.mData.addAll(data);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_contact_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactUiModel data = mData.get(position);
        holder.userName.setText(data.getUser().getName());

        if (data.getUnread()) {
            holder.unreadStatus.setVisibility(View.VISIBLE);
            holder.subText.setVisibility(View.VISIBLE);
            holder.subText.setText(R.string.default_unread);
            holder.subText.setTextColor(Color.parseColor("#FF57A5A0"));
        } else {
            holder.unreadStatus.setVisibility(View.INVISIBLE);
            holder.subText.setText(R.string.default_read);
            holder.subText.setVisibility(View.VISIBLE);
            holder.subText.setTextColor(Color.parseColor("#FF868686"));
        }
        Helper.getInstance().imageLoader(holder, holder.userAvatar, getData().get(position).getUser().getUserAvatars().getUrl());
        holder.lastMessageTime.setText(TimeParser.timeParser(data.getUpdated()));
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userName;
        private ImageView unreadStatus;
        private ImageView userAvatar;
        private TextView lastMessageTime;
        private TextView subText;

        ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            unreadStatus = itemView.findViewById(R.id.status);
            userAvatar = itemView.findViewById(R.id.userImage);
            lastMessageTime = itemView.findViewById(R.id.readTime);
            subText = itemView.findViewById(R.id.userLastMessage);

            itemView.setOnClickListener(this);
            userAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.equals(userAvatar)) {
                if (mAvatarListener != null)
                    mAvatarListener.onItemClick(userAvatar, getAdapterPosition());
                return;
            }
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());

        }

    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface AvatarClickListener {
        void onItemClick(View view, int position);
    }

}
