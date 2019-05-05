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
import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ItemViewType;
import com.upgradedsoftware.android.chat.models.MessageStatus;
import com.upgradedsoftware.android.chat.utils.ChatDiff;
import com.upgradedsoftware.android.chat.utils.SoundMaster;
import com.upgradedsoftware.android.chat.utils.TimeParser;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ChatUiModel> mData;
    private final List<ChatUiModel> cacheData = new ArrayList<>();

    public void addToCache(ChatUiModel element) {
        cacheData.add(element);
    }

    public void setNewData(List<ChatUiModel> data) {
        List<ChatUiModel> temp = new ArrayList<>(data);

        // Если кэш пуст, идём сразу к DiffUtil <3
        if (!cacheData.isEmpty()) {

            // Находим элемент, который прокэширован локально, но не имеет статуса MESSAGE_DELIVERED
            // Если таковой имеется, то удаляем его из локального кэша и ставим ему статус MESSAGE_SAVED_ON_SERVER
            // Для того, чтобы заюзать UI эффекты

            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < cacheData.size(); j++) {
                    if (data.get(i).getTextMessage().equals(cacheData.get(j).getTextMessage()) &&
                            data.get(i).getMessageStatus() != (cacheData.get(j).getMessageStatus()) &&
                            data.get(i).getMessageID().equals(cacheData.get(j).getMessageID())) {
                        cacheData.remove(j);
                        temp.get(i).setMessageStatus(MessageStatus.MESSAGE_SAVED_ON_SERVER);
                        break;
                    }
                }
            }
            temp.addAll(cacheData);
        }

        final ChatDiff diffCallback = new ChatDiff(temp, this.mData);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.mData.clear();
        this.mData.addAll(temp);
        diffResult.dispatchUpdatesTo(this);
    }

    public ChatAdapter(List<ChatUiModel> data) {
        this.mData = data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater;
        if (viewType == ItemViewType.FOR) {
            mInflater = LayoutInflater.from(viewGroup.getContext());
            View view = mInflater.inflate((R.layout.item_message_for_user), viewGroup, false);
            return new ViewHolderForMe(view);
        } else {
            mInflater = LayoutInflater.from(viewGroup.getContext());
            View view = mInflater.inflate((R.layout.item_message_from_user), viewGroup, false);
            return new ViewHolderFromMe(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == ItemViewType.FOR) {
            ViewHolderForMe viewHolderForMe = (ViewHolderForMe) viewHolder;
            viewHolderForMe.bind(mData.get(position));

        } else {
            ViewHolderFromMe viewHolderFromMe = (ViewHolderFromMe) viewHolder;
            viewHolderFromMe.bind(mData.get(position));

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getFromMe())
            return ItemViewType.FROM;
        else
            return ItemViewType.FOR;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ViewHolderForMe extends RecyclerView.ViewHolder {

        private final TextView message;
        private final TextView time;

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

        private final TextView message;
        private final TextView time;
        private final RelativeLayout root;
        private final ProgressBar progressBar;

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

            switch (item.getMessageStatus()) {
                case MessageStatus.MESSAGE_SENDED:
                    setMessageCachedUI();
                    break;
                case MessageStatus.MESSAGE_SAVED_ON_SERVER:
                    setMessageCachedSavedUI();
                    break;
                default:
                    setDefaultUI();
                    break;
            }

        }

        private void setMessageCachedUI() {
            time.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            root.setBackgroundResource(R.drawable.rounded_corner_from_you_cached_saved);
        }

        private void setMessageCachedSavedUI() {
            progressBar.setVisibility(View.GONE);
            time.setVisibility(View.VISIBLE);
            doAnimate();
            SoundMaster.getInstance().playNotification();
        }

        private void doAnimate() {
            AnimationDrawable anim;
            root.setBackgroundResource(R.drawable.animation_message);
            anim = (AnimationDrawable) root.getBackground();
            anim.setEnterFadeDuration(250);
            anim.setExitFadeDuration(100000);
            anim.start();
        }

        private void setDefaultUI() {
            time.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            root.setBackgroundResource(R.drawable.rounded_corner_from_you);
        }
    }
}
