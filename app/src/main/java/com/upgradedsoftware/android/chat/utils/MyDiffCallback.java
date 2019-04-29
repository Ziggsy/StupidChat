package com.upgradedsoftware.android.chat.utils;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.upgradedsoftware.android.chat.models.ContactUiModel;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback{

    private List<ContactUiModel> oldData;
    private List<ContactUiModel> newData;

    public MyDiffCallback(List<ContactUiModel> newData, List<ContactUiModel> oldData) {
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
        return oldData.get(oldItemPosition).equals(newData.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
