package com.example.finalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;

public class GalleryDriver extends AppCompatActivity {

    Context c;
    LinearLayout gallery;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        c = this.getApplicationContext();
        gallery = findViewById(R.id.galleryV);

        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView im = new ImageView(c);

        // TODO Load image(s) from server path
        // Maybe append all files with the userID of the person who took it so it will be easy to get all images?
        // Loop through path and count the number of images
        // For each file found, load it into the view
        Ion.with(im)
                .load("http://example.com/image.png");
        gallery.addView(im, imParams);
        //
    }
}
