package com.example.finalproject;

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
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

public class NotificationFragment extends Fragment {

    String[] not = {
            "New cache found!", "Someone liked your cache!", "Jim accepted your friend request!", "You've received a new friend request!",
            "There are 24 new caches in your area!", "Someone commented on your cache!", "You have 5 new notifications!", "Finish setting up your profile!",
            "Test1", "Test2", "Test3", "Test4"
    };
    Random rand = new Random();
    int k, last;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // region layouts

        // Creates the parent layout to hold other layout and to allow scrolling
        ScrollView parent = new ScrollView(getActivity());
        parent.setSmoothScrollingEnabled(true);
        parent.setHorizontalScrollBarEnabled(true);

        // Creates a layout to hold a grid of notifications
        GridLayout grid = new GridLayout(getActivity());
        grid.setAlignmentMode(GridLayout.ALIGN_BOUNDS);
        grid.getUseDefaultMargins();
        grid.setColumnCount(1);

        // endregion

        // region notifications

        for (int i = 0; i < 26; i++) {
            k = rand.nextInt(not.length);
            while (k == last) {
                k = rand.nextInt(not.length);
            }
            TextView tv = new TextView(getActivity());
            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.leftMargin = 10;
            param.topMargin = 70;
            param.rightMargin = 10;
            // Adds a margin to the last item in the list
            if (i == 25) {
                param.bottomMargin = param.topMargin;
            }
            param.setGravity(Gravity.CENTER);
            param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            tv.setText(not[k]);
            last = k;
            tv.setBackgroundResource(R.drawable.back);

            grid.addView(tv, param);
        }

        // endregion

        // hierarchy
        parent.addView(grid);

        return parent;
    }

}
