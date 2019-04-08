package com.example.finalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout parent = new LinearLayout(getActivity());
        TextView tv = new TextView(getActivity());

        parent.setOrientation(LinearLayout.VERTICAL);

        tv.setText("Testing for testing purposes");

        parent.addView(tv);

        return parent;
    }

}
