package com.upgradedsoftware.android.chat.models;

public class MessageRequestModel {
    private String chatID;
    private String textMessage;
    private Long created;

    public MessageRequestModel(String chatID, String textMessage, Long created){
        this.chatID = chatID;
        this.textMessage = textMessage;
        this.created = created;
    }

    public String getMessageID() {
        return chatID;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public Long getCreated() {
        return created;
    }
}
