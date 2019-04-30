package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.example.finalproject.ServerCommunication.ChangePassword;
import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class UserProfileFragment extends Fragment
{
    //private EditText userName;
    Button editbtn;
    Button followbtn, btnChange;
    EditText editChange;
    Context c;
    AlertDialogManager alert = new AlertDialogManager();
    ImageView profilePicture;
    private static final int RESULT_LOAD_IMAGE = 1;
    private DrawerLayout drawer; // for the drawer menu
    private SessionManagement session; // For accessing the current user info


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_profile,container, false);
        c = this.getContext();

        // Get the user's info from the session
        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> userInfo = session.getUserDetails();

        // Retrieve username and email then display it on the profile page
        final TextView userName = view.findViewById(R.id.user_name);
        TextView userEmail = view.findViewById(R.id.profileEmail);

        final String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);

        userName.setText(userToDisplay);
        userEmail.setText(emailToDisplay);

        // Set up the edit button and follow button
        editbtn = view.findViewById(R.id.editProfile);

        // Redirects user to the settings activity where they can
        // make changes to the password and email
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsPage = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsPage);
            }
        });

        /*
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
        });*/

        // Change profile picture
        profilePicture = view.findViewById(R.id.profileAvatar);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        return inflater.inflate(R.layout.fragment_user_profile,container, false);
    }

    // Opens the gallery to allow for user to choose an image
    public void openGallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    //Change and displays the new profile picture
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_LOAD_IMAGE){
            // Address of the image
            Uri imageUri = data.getData();

            // Display the selected image
            profilePicture.setImageURI(imageUri);
        }
    }
}