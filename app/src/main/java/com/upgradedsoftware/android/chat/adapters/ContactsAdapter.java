package com.upgradedsoftware.android.chat.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.models.ContactUiModel;
import com.upgradedsoftware.android.chat.utils.DataHolder;
import com.upgradedsoftware.android.chat.utils.TimeParser;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<ContactUiModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private AvatarClickListener mAvatarListener;

    public ContactsAdapter(List<ContactUiModel> data, ItemClickListener listener, AvatarClickListener mAvatarListener) {
        this.mData = data;
        this.mClickListener = listener;
        this.mAvatarListener = mAvatarListener;
    }

    public List<ContactUiModel> getData(){
        return mData;
    }

    public void setNewData(List<ContactUiModel> data){
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_contact_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContactUiModel data = mData.get(position);
        holder.userName.setText(data.getUser().getName());

        if(data.getUnread()) {
            holder.unreadStatus.setVisibility(View.VISIBLE);
            holder.subText.setVisibility(View.VISIBLE);
            holder.subText.setText(R.string.default_unread);
            holder.subText.setTextColor(Color.parseColor("#FF57A5A0"));
        }
        else {
            holder.unreadStatus.setVisibility(View.INVISIBLE);
            holder.subText.setText(R.string.default_read);
            holder.subText.setVisibility(View.VISIBLE);
            holder.subText.setTextColor(Color.parseColor("#FF868686"));
        }

        if(DataHolder.getInstance().imageMap != null)
            if(DataHolder.getInstance().imageMap.containsKey(data.getUser().getUserId())) {
                holder.userAvatar.setImageBitmap(DataHolder.getInstance().imageMap.get(data.getUser().getUserId()));
            }

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
            if (view.equals(userAvatar)){
                if (mAvatarListener != null) mAvatarListener.onItemClick(userAvatar, getAdapterPosition());
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
