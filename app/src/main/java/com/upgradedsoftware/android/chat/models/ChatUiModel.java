package com.upgradedsoftware.android.chat.models;

public class ChatUiModel {
    private String messageID;
    private Boolean fromMe;
    private String textMessage;
    private Long created;
    private Integer mMessageStatus;

    public ChatUiModel(String messageID, Boolean fromMe, String textMessage, Long created){
        this.messageID = messageID;
        this.fromMe = fromMe;
        this.textMessage = textMessage;
        this.created = created;
        this.mMessageStatus = MessageStatus.MESSAGE_OK;
    }

    public ChatUiModel(String messageID, Boolean fromMe, String textMessage, Long created, Integer status){
        this.messageID = messageID;
        this.fromMe = fromMe;
        this.textMessage = textMessage;
        this.created = created;
        this.mMessageStatus = status;
    }


    public String getMessageID() {
        return messageID;
    }

    public Boolean getFromMe() {
        return fromMe;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public Long getCreated() {
        return created;
    }

    public Integer getMessageStatus() {
        return mMessageStatus;
    }

    public void setMessageStatus(int status) {
        mMessageStatus = status;
    }
}

