package com.upgradedsoftware.android.chat.utils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.upgradedsoftware.android.chat.R;

import java.util.Objects;

import static com.upgradedsoftware.android.chat.utils.Helper.KEY_AVATAR_URL;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_CHAT_ID;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_NAME;
import static com.upgradedsoftware.android.chat.utils.Helper.KEY_USER_SETTINGS;
import static com.upgradedsoftware.android.chat.utils.Helper.URL_MY_AVATAR;


public class BottomSheetDialog extends BottomSheetDialogFragment {
    private String id = "";
    private String userName = "";
    private String userInfo = "";
    private String url = "";

    public void setId(String id) {
        this.id = id;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        restoreData(savedInstanceState);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false);
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Исправление баг с landscape
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final Dialog dialog = getDialog();
                FrameLayout bottomSheet = dialog.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        // Ничего не надо делать :>
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        dialog.dismiss();
                    }
                });
                behavior.setPeekHeight(0);
            }
        });

        if (id.isEmpty() && userName.isEmpty()) {
            ImageView viewAvatar = getView().findViewById(R.id.myAvatar);
            Helper.getInstance().imageLoader(viewAvatar, URL_MY_AVATAR);
        } else {
            loadUser();
        }
    }

    private void restoreData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(KEY_AVATAR_URL, "");
            userName = savedInstanceState.getString(KEY_USER_NAME, "");
            userInfo = savedInstanceState.getString(KEY_USER_SETTINGS, "");
            id = savedInstanceState.getString(KEY_CHAT_ID, "");
        }
    }

    private void loadUser() {
        TextView text = getView().findViewById(R.id.myFirstAndLast);
        text.setText(userName);
        TextView info = getView().findViewById(R.id.mySubString);
        info.setText(userInfo);
        ImageView viewAvatar = getView().findViewById(R.id.myAvatar);
        Helper.getInstance().imageLoader(viewAvatar, url);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(KEY_AVATAR_URL, url);
        outState.putString(KEY_USER_NAME, userName);
        outState.putString(KEY_USER_SETTINGS, userInfo);
        outState.putString(KEY_CHAT_ID, id);
        super.onSaveInstanceState(outState);
    }
}
