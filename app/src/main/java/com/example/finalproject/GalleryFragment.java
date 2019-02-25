package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

public class GalleryFragment extends Fragment {

    Context c;
    LinearLayout gallery;
    View view;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        c = this.getContext();
        gallery = view.findViewById(R.id.LinearP);

        gallery.addView(makeGallery(55));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }

    private LinearLayout makeGallery(int userID) {
        LinearLayout linear = new LinearLayout(this.getContext());

        // region TESTING

        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        @SuppressLint("StaticFieldLeak")
        ImageView im = new ImageView(c);


        for (int i = 0; i < 4; i++) {
            Glide.with(this)
                    .load("https://moodle.htwchur.ch/pluginfile.php/124614/mod_page/content/4/example.jpg")
                    .into(im);
            linear.addView(im, imParams);
        }

        // endregion

        return linear;
    }
}
