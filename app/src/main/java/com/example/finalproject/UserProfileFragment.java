package com.example.finalproject;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.ServerCommunication.SessionManagement;

import java.io.InputStream;
import java.util.HashMap;

public class UserProfileFragment extends Fragment {
    Button editbtn, followbtn;
    private SessionManagement session; // For accessing the current user info
    ImageView profilePicture;
    private static final int RESULT_LOAD_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // Inflate the view
        View view = inflater.inflate(R.layout.fragment_user_profile,container, false);

        // Get the user's info from the session
        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> userInfo = session.getUserDetails();

        // Set the user information to appropriate fields
        TextView userName = view.findViewById(R.id.user_name);
        final TextView userEmail = view.findViewById(R.id.profileEmail);
        final TextView userPhone = getView().findViewById(R.id.phoneNum);

        // Grabs user information from shared preferences
        String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        final String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);
        final String phoneToDisplay = userInfo.get(SessionManagement.KEY_PHONE);

        // Display user information
        userName.setText(userToDisplay);
        userEmail.setText(emailToDisplay);
        userPhone.setText(phoneToDisplay);

        // Set up buttons
        editbtn = getView().findViewById(R.id.editProfile);
        followbtn = getView().findViewById(R.id.followBtn);

        // Change profile picture
        profilePicture = getView().findViewById(R.id.profileAvatar);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Redirects user to the settings page where they can
        // make changes to the password, email, and phone
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsPage = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsPage);
            }
        });

        // Follow the user
        /*TODO: 1+ in follower counter and add follower to list*/
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

    private void openGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_LOAD_IMAGE){
            // Address of the image
            Uri imageUri = data.getData();

            // Display the selected image
            profilePicture.setImageURI(imageUri);
        }
        return view;
    }
}
