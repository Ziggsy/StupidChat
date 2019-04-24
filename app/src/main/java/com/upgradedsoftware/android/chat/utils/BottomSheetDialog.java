package com.upgradedsoftware.android.chat.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.upgradedsoftware.android.chat.R;

import static com.upgradedsoftware.android.chat.utils.Helper.URL_MY_AVATAR;


public class BottomSheetDialog extends BottomSheetDialogFragment {
    public String id = "";
    public String userName = "";
    public String userInfo = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (id.isEmpty() && userName.isEmpty()) {
            downloadImage(URL_MY_AVATAR);
        } else {
            loadUser();
        }
    }

    private void loadUser() {
        TextView text = getView().findViewById(R.id.myFirstAndLast);
        text.setText(userName);
        TextView info = getView().findViewById(R.id.mySubString);
        info.setText(userInfo);
        ImageView image = getView().findViewById(R.id.myAvatar);
        image.setImageBitmap(DataHolder.getInstance().imageMap.get(id));
    }

    private void downloadImage(String url) {
        Glide.with(this)
                .asBitmap()
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        ImageView view = getView().findViewById(R.id.myAvatar);
                        view.setImageBitmap(resource);
                    }
                });
    }
}
