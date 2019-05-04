package com.upgradedsoftware.android.chat.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ContactUiModel implements Comparable<ContactUiModel>, Parcelable {
    private final String chatId;
    private final long updated;
    private final long created;
    private final boolean unread;
    private UserModel user;
    private final String lastMessage;

    public ContactUiModel(String chatId, long updated, long created, boolean unread, UserModel user, String lastMessage) {
        this.chatId = chatId;
        this.updated = updated;
        this.created = created;
        this.unread = unread;
        this.user = user;
        this.lastMessage = lastMessage;
    }


    private ContactUiModel(Parcel in) {
        chatId = in.readString();
        updated = in.readLong();
        created = in.readLong();
        unread = in.readByte() != 0;
        lastMessage = in.readString();
    }

    public static final Creator<ContactUiModel> CREATOR = new Creator<ContactUiModel>() {
        @Override
        public ContactUiModel createFromParcel(Parcel in) {
            return new ContactUiModel(in);
        }

        @Override
        public ContactUiModel[] newArray(int size) {
            return new ContactUiModel[size];
        }
    };

    public String getChatId() {
        return chatId;
    }

    public long getUpdated() {
        return updated;
    }

    public long getCreated() {
        return created;
    }

    public boolean getUnread() {
        return unread;
    }

    public UserModel getUser() {
        return user;
    }

    public String getLastMessage() {
        return lastMessage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(chatId);
        parcel.writeLong(updated);
        parcel.writeLong(created);
        parcel.writeByte((byte) (unread ? 1 : 0));
        parcel.writeString(lastMessage);
    }

    @Override
    public int compareTo(@NonNull ContactUiModel contactUiModel) {
        Long compareUpdated = (contactUiModel).getUpdated();
        return (int) (this.updated - compareUpdated.intValue());
    }

}

