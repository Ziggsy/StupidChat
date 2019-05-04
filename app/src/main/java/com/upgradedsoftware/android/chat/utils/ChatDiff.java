package com.upgradedsoftware.android.chat.utils;

import android.support.v7.util.DiffUtil;

import com.upgradedsoftware.android.chat.models.ChatUiModel;

import java.util.List;

public class ChatDiff extends DiffUtil.Callback {

    private final List<ChatUiModel> oldData;
    private final List<ChatUiModel> newData;

    public ChatDiff(List<ChatUiModel> newData, List<ChatUiModel> oldData) {
        this.newData = newData;
        this.oldData = oldData;
    }

    @Override
    public int getOldListSize() {
        return oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldData.get(oldItemPosition).getMessageID().equals(newData.get(newItemPosition).getMessageID()));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldData.get(oldItemPosition).getFromMe() == (newData.get(oldItemPosition).getFromMe()) &&
                oldData.get(oldItemPosition).getCreated() == (newData.get(oldItemPosition).getCreated()) &&
                oldData.get(oldItemPosition).getTextMessage().equals(newData.get(oldItemPosition).getTextMessage()) &&
                oldData.get(oldItemPosition).getMessageStatus() == (newData.get(oldItemPosition).getMessageStatus())
        );


    }

}
