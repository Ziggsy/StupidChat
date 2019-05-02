package com.upgradedsoftware.android.chat.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
