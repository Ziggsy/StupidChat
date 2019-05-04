package com.upgradedsoftware.android.chat.utils;

import android.support.v7.util.DiffUtil;

import com.upgradedsoftware.android.chat.models.ContactUiModel;

import java.util.List;

public class ContactDiff extends DiffUtil.Callback {

    private final List<ContactUiModel> oldData;
    private final List<ContactUiModel> newData;

    public ContactDiff(List<ContactUiModel> newData, List<ContactUiModel> oldData) {
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
        return oldData.get(oldItemPosition).getChatId().equals(newData.get(newItemPosition).getChatId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return (oldData.get(oldItemPosition).getUnread() == (newData.get(oldItemPosition).getUnread()) &&
                oldData.get(oldItemPosition).getCreated() == (newData.get(oldItemPosition).getCreated()) &&
                oldData.get(oldItemPosition).getUpdated() == (newData.get(oldItemPosition).getUpdated()) &&
                oldData.get(oldItemPosition).getLastMessage().equals(newData.get(oldItemPosition).getLastMessage()) &&
                oldData.get(oldItemPosition).getUser().getUserId().equals(newData.get(oldItemPosition).getUser().getUserId()) &&
                oldData.get(oldItemPosition).getUser().getName().equals(newData.get(oldItemPosition).getUser().getName()) &&
                oldData.get(oldItemPosition).getUser().getUserAvatars().getUrl().equals(newData.get(oldItemPosition).getUser().getUserAvatars().getUrl()) &&
                oldData.get(oldItemPosition).getUser().getSettings().getWork().equals(newData.get(oldItemPosition).getUser().getSettings().getWork())
        );
    }

}
