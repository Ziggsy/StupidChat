package com.upgradedsoftware.android.chat.models;

import android.support.annotation.IntDef;


@IntDef({MessageStatus.MESSAGE_DELIVERED, MessageStatus.MESSAGE_SENDED, MessageStatus.MESSAGE_SAVED_ON_SERVER, MessageStatus.MESSAGE_ERROR})
public @interface MessageStatus {
    int MESSAGE_DELIVERED = 0;
    int MESSAGE_SENDED = 1;
    int MESSAGE_SAVED_ON_SERVER = 2;
    int MESSAGE_ERROR = 3;
}
