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

import com.example.finalproject.ServerCommunication.ChangePassword;
import com.example.finalproject.ServerCommunication.SessionManagement;

import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class UserProfileFragment extends Fragment {
    Button followbtn, savebtn, btnChange, editbtn;
    EditText editChange;
    Context c;
    AlertDialogManager alert = new AlertDialogManager();
    private DrawerLayout drawer; // for the drawer menu
    private SessionManagement session; // For accessing the current user info
    ImageView profilePicture;
    private static final int RESULT_LOAD_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the view
        // Inflate the view
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // Get the user's info from the session
        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> userInfo = session.getUserDetails();
        c = this.getContext();

        // Set the user information to appropriate fields
        TextView userName = view.findViewById(R.id.user_name);
        final TextView userEmail = view.findViewById(R.id.profileEmail);

        // Grabs user information from shared preferences
        final String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);

        // Display user information
        userName.setText(userToDisplay);
        userEmail.setText(emailToDisplay);

        // Set up buttons
        editbtn = view.findViewById(R.id.edit_profile);
        //followbtn = view.findViewById(R.id.followBtn); Will possibly implement in the future. Outside of the scope of this semester

        // Change profile picture
        profilePicture = view.findViewById(R.id.profileAvatar);
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

        /* Follow the user (Will possibly implement in the future. Outside of the scope of this semester)
        //TODO: 1+ in follower counter and add follower to list
        followbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followbtn.getText().toString().equalsIgnoreCase("Follow me")) {
                    followbtn.setText("Following");
                } else {
                    followbtn.setText("Follow me");
                }
            }
        }); */

        // CODE FOR CHANGING PASSWORD -Jordan
        editChange = view.findViewById(R.id.txt_ChangePass);
        btnChange = view.findViewById(R.id.btn_ChangePass);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pass = String.valueOf(editChange.getText());
                ChangePassword changePassword = new ChangePassword(c);

                try {
                    // Get the result of the login back from the server and check to see if the login was successful
                    String result = changePassword.execute(userToDisplay, new_pass).get();
                    if (result.contains("Success")) {
                        alert.showAlertDialog(c, "Change Completed", result, false);
                    } else {
                        alert.showAlertDialog(c, "Change Failed", result, false);
                    }

                    // Else, an error will be thrown from the BackgroundWorker and be displayed to the user
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            // Address of the image
            Uri imageUri = data.getData();

            // Display the selected image
            profilePicture.setImageURI(imageUri);
        }
    }
}
