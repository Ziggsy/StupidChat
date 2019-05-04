package com.upgradedsoftware.android.chat.utils;

import android.app.Dialog;
import android.content.DialogInterface;
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

import static com.upgradedsoftware.android.chat.utils.Helper.URL_MY_AVATAR;


public class BottomSheetDialog extends BottomSheetDialogFragment {
    public String id = "";
    public String userName = "";
    public String userInfo = "";
    public String url = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false);
    }

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
//                        dialog.dismiss();
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

    private void loadUser() {
        TextView text = getView().findViewById(R.id.myFirstAndLast);
        text.setText(userName);
        TextView info = getView().findViewById(R.id.mySubString);
        info.setText(userInfo);
        ImageView viewAvatar = getView().findViewById(R.id.myAvatar);
        Helper.getInstance().imageLoader(viewAvatar, url);
    }

}
