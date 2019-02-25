package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
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

import com.koushikdutta.ion.Ion;

public class GalleryFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    static Context c;
    @SuppressLint("StaticFieldLeak")
    static LinearLayout gallery;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container,false);
        c = this.getContext();
        gallery = view.findViewById(R.id.galleryV);

        // region TESTING

        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        @SuppressLint("StaticFieldLeak")
        ImageView im = new ImageView(c);

        //new GalleryFragment.FetchDB().execute();

        for (int i = 0; i < 4; i++) {
            Ion.with(im)
                    .load("https://moodle.htwchur.ch/pluginfile.php/124614/mod_page/content/4/example.jpg");
            if (im.getParent() != null) {
                ((ViewGroup)im.getParent()).removeView(im);
            }
            gallery.addView(im, imParams);
        }

        // endregion

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private static class FetchDB extends AsyncTask<Integer, Integer, Integer> {
        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        @SuppressLint("StaticFieldLeak")
        ImageView im = new ImageView(c);

        // TODO Get userID from current user
        String userID = "44";
        String SQLQ = "SELECT filePath FROM images WHERE CONTAINS(fileName, ?)";

        @Override
        protected Integer doInBackground(Integer... integers) {
            // rawQuery("SELECT filePath FROM images WHERE CONTAINS(fileName, ?)", new String[] {userID})
            // TODO Load image(s) from server path
            // Maybe append all files with the userID of the person who took it so it will be easy to get all images?
            // For each file found, load it into the view
            for (int i = 0; i < 4; i++) {
                Ion.with(im)
                        .load("https://moodle.htwchur.ch/pluginfile.php/124614/mod_page/content/4/example.jpg");
                gallery.addView(im, imParams);
            }
            //
            return null;
        }
    }
}
