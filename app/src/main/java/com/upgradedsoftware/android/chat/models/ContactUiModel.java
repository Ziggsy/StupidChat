package com.upgradedsoftware.android.chat.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactUiModel implements Comparable, Parcelable {
    private String chatId;
    private Long updated;
    private Long created;
    private Boolean unread;
    private UserModel user;

    public ContactUiModel(String chatId, Long updated, Long created, Boolean unread, UserModel user) {
        this.chatId = chatId;
        this.updated = updated;
        this.created = created;
        this.unread = unread;
        this.user = user;
    }

    protected ContactUiModel(Parcel in) {
        chatId = in.readString();
        if (in.readByte() == 0) {
            updated = null;
        } else {
            updated = in.readLong();
        }
        if (in.readByte() == 0) {
            created = null;
        } else {
            created = in.readLong();
        }
        byte tmpUnread = in.readByte();
        unread = tmpUnread == 0 ? null : tmpUnread == 1;
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

    public Long getUpdated() {
        return updated;
    }

    public Long getCreated() {
        return created;
    }

    public Boolean getUnread() {
        return unread;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public int compareTo(Object object) {
        Long compareUpdated = ((ContactUiModel) object).getUpdated();
        return this.updated.intValue() - compareUpdated.intValue();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chatId);
        if (updated == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(updated);
        }
        if (created == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(created);
        }
        dest.writeByte((byte) (unread == null ? 0 : unread ? 1 : 2));
    }
}

