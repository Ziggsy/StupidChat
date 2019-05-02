package com.upgradedsoftware.android.chat.models;

import android.support.annotation.IntDef;


@IntDef({MessageStatus.MESSAGE_OK, MessageStatus.MESSAGE_CACHED, MessageStatus.MESSAGE_CACHED_SAVED, MessageStatus.MESSAGE_ERROR})
public @interface MessageStatus {
    int MESSAGE_OK = 0;
    int MESSAGE_CACHED = 1;
    int MESSAGE_CACHED_SAVED = 2;
    int MESSAGE_ERROR = 3;
}
