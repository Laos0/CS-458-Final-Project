package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.HashMap;

public class UserProfileFragment extends Fragment {
    Button editbtn, followbtn;
    private SessionManagement session; // For accessing the current user info
    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get the user's info from the session
        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> userInfo = session.getUserDetails();

        // Set the user information
        TextView userName = getView().findViewById(R.id.user_name);
        final TextView userEmail = getView().findViewById(R.id.profileEmail);
        final TextView userPhone = getView().findViewById(R.id.phoneNum);

        String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        final String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);
        final String phoneToDisplay = "123-456-7890";

        userName.setText(userToDisplay);
        userEmail.setText(emailToDisplay);
        userPhone.setText(phoneToDisplay);

        //set up buttons
        editbtn = getView().findViewById(R.id.edit_profile);
        followbtn = getView().findViewById(R.id.followBtn);

        //intent = new Intent(getActivity(), Contact_Developer.class);

        //edit email and phone number
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //redirect to settings page
                startActivity(intent);
            }
        });

        //follow the user
        //TO DO: 1+ in follower counter and add follower to list
        followbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followbtn.getText().toString().equalsIgnoreCase("Follow me")) {
                    followbtn.setText("Following");
                } else {
                    followbtn.setText("Follow me");
                }
            }
        });

        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }
}
