package com.example.finalproject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.URL;

public class GalleryFragment extends Fragment {

    public static Bitmap image;
    @SuppressLint("StaticFieldLeak")
    public static ImageView im;
    public static GridLayout.LayoutParams param = new GridLayout.LayoutParams();
    @SuppressLint("StaticFieldLeak")
    public static ScrollView DAD;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout linear;
    @SuppressLint("StaticFieldLeak")
    public static GridLayout grid;
    @SuppressLint("StaticFieldLeak")
    public static Activity a;
    String[] eatFoodyImages = {
            "http://i.imgur.com/rFLNqWI.jpg",
            "http://i.imgur.com/C9pBVt7.jpg",
            "http://i.imgur.com/rT5vXE1.jpg",
            "http://i.imgur.com/aIy5R2k.jpg",
            "http://i.imgur.com/MoJs9pT.jpg",
            "http://i.imgur.com/S963yEM.jpg",
            "http://i.imgur.com/rLR2cyc.jpg",
            "http://i.imgur.com/SEPdUIx.jpg",
            "http://i.imgur.com/aC9OjaM.jpg",
            "http://i.imgur.com/76Jfv9b.jpg",
            "http://i.imgur.com/fUX7EIB.jpg",
            "http://i.imgur.com/syELajx.jpg",
            "http://i.imgur.com/COzBnru.jpg",
            "http://i.imgur.com/Z3QjilA.jpg",
    };

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        a = getActivity();

        DAD = new ScrollView(getActivity());
        DAD.setSmoothScrollingEnabled(true);
        DAD.setHorizontalScrollBarEnabled(true);

        linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);

        grid = new GridLayout(getActivity());
        grid.setColumnCount(3);
        grid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        grid.getUseDefaultMargins();

        // TODO Replace # with amount of found images for current user
        for (String eatFoodyImage : eatFoodyImages) {
            try {
                URL url = new URL(eatFoodyImage);
                new Async().execute(url);
            } catch (IOException e) {
                Log.i("TEST", e.toString());
            }
        }

        linear.addView(grid);
        DAD.addView(linear);

        return DAD;
    }
}

class Async extends AsyncTask<URL, Integer, Bitmap> {

    @Override
    protected Bitmap doInBackground(URL... urls) {
        try {
            return BitmapFactory.decodeStream(urls[0].openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        // super.onPostExecute(bitmap);

        GalleryFragment.im.setScaleType(ImageView.ScaleType.FIT_XY);
        // GridLayout.LayoutParams.WRAP_CONTENT
        // param.height = 200;
        // param.width = 400;
        GalleryFragment.param.rightMargin = 5;
        GalleryFragment.param.topMargin = 5;
        GalleryFragment.param.setGravity(Gravity.CENTER);
        GalleryFragment.param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        GalleryFragment.param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        Glide.with(GalleryFragment.a)
                // TODO Replace URL with location of user's photo(s)
                .load(Bitmap.createScaledBitmap(GalleryFragment.image, 120, 120, false))
                .into(GalleryFragment.im);
        GalleryFragment.grid.addView(GalleryFragment.im, GalleryFragment.param);

        GalleryFragment.image = bitmap;
    }
}