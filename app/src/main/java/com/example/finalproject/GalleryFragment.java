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

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    // region declaration

    // Declare a testing array of images
    ArrayList<String> galleryImages = new ArrayList<>();

    // endregion

    // Creates the fragment view for programmatic use
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // region layouts

        // Creates the parent layout to hold other layouts and to allow scrolling
        ScrollView parent = new ScrollView(getActivity());
        parent.setSmoothScrollingEnabled(true);
        parent.setHorizontalScrollBarEnabled(true);

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

        galleryImages.add("http://144.13.22.48/CS458SP19/Team2/pictures/20190410_134727.jpg");

        // For each URL in the array, set an imageview to that image
        for (String image : galleryImages) {
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
            // Loads the image into im using Glide
            Glide.with(this)
                    .load(image)
                    .into(im);
            grid.addView(im, param);
        }

        // endregion

        // region hierarchy

        // Adds the layouts to each other
        // grid.addView(im);
        linear.addView(grid);
        parent.addView(linear);

        // endregion

        // Displays the parent layout with all elements inside
        return parent;
    }
}