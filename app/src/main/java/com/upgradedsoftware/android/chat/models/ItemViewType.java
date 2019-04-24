package com.upgradedsoftware.android.chat.models;

import android.support.annotation.IntDef;


@IntDef({ItemViewType.FOR, ItemViewType.FROM})
public @interface ItemViewType {
    int FOR = 0;
    int FROM = 1;
}
