package com.example.finalproject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;

public class GalleryFragment extends Fragment {

    // region declaration

    // Declare a testing array of images
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

    // endregion

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // region layouts

        // Creates the parent layout to hold other layouts and to allow scrolling
        ScrollView DAD = new ScrollView(getActivity());
        DAD.setSmoothScrollingEnabled(true);
        DAD.setHorizontalScrollBarEnabled(true);

        // Creates a layout to hold the image grid
        LinearLayout linear = new LinearLayout(getActivity());
        linear.setOrientation(LinearLayout.VERTICAL);

        // Creates a layout to hold a grid of images
        GridLayout grid = new GridLayout(getActivity());
        grid.setColumnCount(3);
        grid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        grid.getUseDefaultMargins();

        // endregion

        // region foreach

        // TODO Replace # with amount of found images for current user
        // For each URL in the array, set an imageview to that image
        for (String eatFoodyImage : eatFoodyImages) {
            ImageView im = new ImageView(getActivity());
            im.setScaleType(ImageView.ScaleType.FIT_XY);
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            // GridLayout.LayoutParams.WRAP_CONTENT
            // Sets parameters for im
            param.height = 400;
            param.width = 200;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            // TODO AsyncTask to get image URL(s)
            // Loads the image into im using Glide
            Glide.with(this)
                    // TODO Replace URL with location of user's photo(s)
                    .load(eatFoodyImage)
                    .into(im);
            grid.addView(im, param);
        }

        // endregion

        // Adds the layouts to each other
        linear.addView(grid);
        DAD.addView(linear);

        // Displays the parent layout
        return DAD;
    }
}