package com.upgradedsoftware.android.chat.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.R;
import com.upgradedsoftware.android.chat.models.ChatsUiModel;
import com.upgradedsoftware.android.chat.utils.DataHolder;

import java.util.HashMap;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<ChatsUiModel> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public ContactsAdapter(Context context, List<ChatsUiModel> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    ChatsUiModel getItem(int id) {
        return mData.get(id);
    }

    public void setNewData(List<ChatsUiModel> data){
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_contact_in_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatsUiModel data = mData.get(position);
        holder.userName.setText(data.getUser().getName());

        if(data.getUnread()) holder.unreadStatus.setVisibility(View.VISIBLE);
        else holder.unreadStatus.setVisibility(View.INVISIBLE);

        if(DataHolder.getInstance().imageMap != null) if(DataHolder.getInstance().imageMap.containsKey(data.getUser().getUserId()))
            holder.userAvatar.setImageBitmap(DataHolder.getInstance().imageMap.get(data.getUser().getUserId()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView userName;
        ImageView unreadStatus;
        ImageView userAvatar;

        ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            unreadStatus = itemView.findViewById(R.id.status);
            userAvatar = itemView.findViewById(R.id.userImage);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
