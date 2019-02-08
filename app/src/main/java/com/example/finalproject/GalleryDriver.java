package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.koushikdutta.ion.Ion;

public class GalleryDriver extends AppCompatActivity {

    Context c;
    LinearLayout gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        c = this.getApplicationContext();
        gallery = findViewById(R.id.gallery);

        LinearLayout.LayoutParams imParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // TODO Change 8 to the number of pictures found in database
        for (int i = 0; i < 8; i++) {
            ImageView im = new ImageView(c);
            Ion.with(im)
                    // TODO Change .load() to the database
                    .load("https://upload.wikimedia.org/wikipedia/commons/b/bf/Test_card.png");
            gallery.addView(im, imParams);
        }
    }
}
