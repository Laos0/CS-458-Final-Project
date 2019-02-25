package com.example.finalproject;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

public class GalleryFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        // LinearLayout gallery = view.findViewById(R.id.LinearC);

        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ScrollView sv = new ScrollView(getActivity());
        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);
        LinearLayout DAD = new LinearLayout(getActivity());
        DAD.setOrientation(LinearLayout.VERTICAL);

        // TODO Replace 4 with amount of found images for current user
        for (int i = 0; i < 4; i++) {
            ImageView im = new ImageView(getActivity());
            Glide.with(this)
                    // TODO Replace URL with location of user's photo(s)
                    .load("https://moodle.htwchur.ch/pluginfile.php/124614/mod_page/content/4/example.jpg")
                    .into(im);
            linear.addView(im, imParams);
            Log.i("TEST", String.valueOf(i));
        }

        sv.addView(linear);
        DAD.addView(sv);

        return DAD;
    }
}
