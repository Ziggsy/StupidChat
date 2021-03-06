package com.upgradedsoftware.android.chat.adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.App;
import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.utils.ContactDiff;
import com.upgradedsoftware.android.chat.utils.Helper;
import com.upgradedsoftware.android.chat.utils.TimeParser;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private final List<ContactUiModel> mData;
    private final ItemClickListener mClickListener;
    private final AvatarClickListener mAvatarListener;

    public ContactsAdapter(List<ContactUiModel> data, ItemClickListener listener, AvatarClickListener mAvatarListener) {
        this.mData = data;
        this.mClickListener = listener;
        this.mAvatarListener = mAvatarListener;
    }

    public List<ContactUiModel> getData() {
        return mData;
    }

    public void setNewData(List<ContactUiModel> data) {
        final ContactDiff diffCallback = new ContactDiff(data, this.mData);
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
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_contact_in_list, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactUiModel data = mData.get(position);
        holder.userName.setText(data.getUser().getName());

        if (data.getUnread()) {
            holder.unreadStatus.setVisibility(View.VISIBLE);
            holder.subText.setVisibility(View.VISIBLE);
            holder.subText.setText(data.getLastMessage());
            holder.subText.setTextColor(ContextCompat.getColor(App.getContext(), R.color.unreadMessage));
        } else {
            holder.unreadStatus.setVisibility(View.INVISIBLE);
            holder.subText.setText(App.getContext().getString(R.string.you, data.getLastMessage()));
            holder.subText.setVisibility(View.VISIBLE);
            holder.subText.setTextColor(ContextCompat.getColor(App.getContext(), R.color.textReadMessageColor));
        }
        Helper.getInstance().imageLoader(holder.userAvatar, getData().get(position).getUser().getUserAvatars().getUrl());
        holder.lastMessageTime.setText(TimeParser.timeParser(data.getUpdated()));
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView userName;
        private final ImageView unreadStatus;
        private final ImageView userAvatar;
        private final TextView lastMessageTime;
        private final TextView subText;

        ContactViewHolder(View itemView) {
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
