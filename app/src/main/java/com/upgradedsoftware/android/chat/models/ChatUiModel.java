package com.upgradedsoftware.android.chat.models;

public class ChatUiModel {
    private final String messageID;
    private final boolean fromMe;
    private final String textMessage;
    private final long created;
    private int mMessageStatus;

    public ChatUiModel(String messageID, boolean fromMe, String textMessage, long created, int status) {
        this.messageID = messageID;
        this.fromMe = fromMe;
        this.textMessage = textMessage;
        this.created = created;
        this.mMessageStatus = status;
    }

    public String getMessageID() {
        return messageID;
    }

    public boolean getFromMe() {
        return fromMe;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public long getCreated() {
        return created;
    }

    public int getMessageStatus() {
        return mMessageStatus;
    }

    public void setMessageStatus(int status) {
        mMessageStatus = status;
    }

}

