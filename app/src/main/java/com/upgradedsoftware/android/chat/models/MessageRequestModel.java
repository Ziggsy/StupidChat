package com.upgradedsoftware.android.chat.models;

public class MessageRequestModel {
    private String messageID;
    private String textMessage;
    private Long created;

    public MessageRequestModel(String messageID, String textMessage, Long created){
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

    public Long getCreated() {
        return created;
    }
}
