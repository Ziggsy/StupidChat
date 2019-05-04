package com.upgradedsoftware.android.chat.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.upgradedsoftware.android.chat.models.ChatUiModel;
import com.upgradedsoftware.android.chat.models.ContactUiModel;

import java.util.List;

public class ChatDiff extends DiffUtil.Callback {

    private List<ChatUiModel> oldData;
    private List<ChatUiModel> newData;

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
        return (oldData.get(oldItemPosition).getMessageID().equals(newData.get(newItemPosition).getMessageID()) &&
        oldData.get(oldItemPosition).getMessageStatus().equals(newData.get(newItemPosition).getMessageStatus())
        );
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            return
                    (oldData.get(oldItemPosition).getFromMe().equals(newData.get(oldItemPosition).getFromMe()) &&
                            oldData.get(oldItemPosition).getCreated().equals(newData.get(oldItemPosition).getCreated()) &&
                            oldData.get(oldItemPosition).getTextMessage().equals(newData.get(oldItemPosition).getTextMessage())
                    );
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
