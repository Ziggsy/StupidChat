package com.upgradedsoftware.android.chat.models;

public class MessageRequestModel {
    private final String messageID;
    private final String textMessage;
    private final long created;

    public MessageRequestModel(String messageID, String textMessage, long created){
        this.messageID = messageID;
        this.textMessage = textMessage;
        this.created = created;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public long getCreated() {
        return created;
    }
}
