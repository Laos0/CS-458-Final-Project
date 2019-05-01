package com.example.finalproject;

import android.annotation.TargetApi;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

// Creates a Callback to change the list variable in AsyncTask
interface Callback {
    void onValueReceived(String value);

    void onFailure();
}

public class GalleryFragment extends Fragment {


    // region declaration

    // Declare a string to hold the list of images
    public static String list = "";
    // Declare an arrayList of images
    ArrayList<String> galleryImages = new ArrayList<>();
    // Declare the base URL for images
    String imageLoc = "http://144.13.22.48/CS458SP19/Team2/";

    // endregion

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    // Creates the fragment view for programmatic use
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

        // region background

        Callback callback = new Callback() {
            @Override
            public void onValueReceived(final String value) {
                list = value;
            }

            @Override
            public void onFailure() {

            }
        };

        new Background(callback).execute(imageLoc + "db/getFiles.php");
        galleryImages.addAll(Arrays.asList(list.split("<br>")));
        Log.d("galleryImages full", galleryImages.toString());

        // endregion

        // region foreach

        // For each URL in the array, set an imageView to that image
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
                    .load(imageLoc + "pictures/" + image)
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

    // endregion
}

class Background extends AsyncTask<String, Void, String> {

    private Callback callback;

    Background(final Callback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(final String... strings) {
        try {
            GalleryFragment.list = "";
            // Build and set timeout values for the request.
            URLConnection connection = (new URL(strings[0])).openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            // Read and store the result line by line then return the entire string.
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder html = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                html.append(line);
            }
            in.close();

            // TEST
            Log.d("HTML response", html.toString());

            return html.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void onPostExecute(final String result) {
        callback.onValueReceived(result);
    }
}
