package com.example.finalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddFragment extends Fragment {

    EditText userAcc;
    Button submitBtn;
    TextView dummyT;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container,false);
        userAcc = view.findViewById(R.id.friendAcc);
        submitBtn = view.findViewById(R.id.submitAdd);
        dummyT = view.findViewById(R.id.dummyText);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Testing code line, to see if my button was working in fragment
                dummyT.setText(userAcc.getText());

                // In this setOnClickListener, it will search for the friend's name in the database
                // If the friend exist, there will be a friend request to that friend
                // The friend will ither accept or decline the request and returns a 0 or 1
                // The 0 represent false, and 1 true
                // If friend accepts the friend request then his account will be under the requested user's friend list

            }
        });

        return view;
    }
}
