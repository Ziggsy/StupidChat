package com.upgradedsoftware.android.chat.models;

public class ChatUiModel {
    private String chatId;
    private Long updated;
    private Long created;
    private Boolean unread;
    private UserModel user;

    public ChatUiModel(String chatId, Long updated, Long created, Boolean unread, UserModel user){
        this.chatId = chatId;
        this.updated = updated;
        this.created = created;
        this.unread = unread;
        this.user = user;
    }

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
}

