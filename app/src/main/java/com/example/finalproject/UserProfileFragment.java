package com.example.finalproject;

import android.content.Context;
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

import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.HashMap;

public class UserProfileFragment extends Fragment
{
    //private EditText userName;
    Button editbtn;
    Button followbtn, savebtn;
    EditText editEmail, editPhone;
    private SessionManagement session; // For accessing the current user info


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        // Get the user's info from the session
        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> userInfo = session.getUserDetails();

        //other shared preferences
        /*
        public static final String MyPREFERENCES = "MyPrefs" ;
        public static final String userPhone = "phoneKey";
        public static final String userEmail = "emailKey";
        SharedPreferences sharedpreferences;
        */

        // Set the user information
        TextView userName = getView().findViewById(R.id.profileUserName);
        final TextView userEmail = getView().findViewById(R.id.profileEmail);
        final TextView userPhone = getView().findViewById(R.id.phoneNum);

        String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);

        userName.setText(userToDisplay);
        userEmail.setText(emailToDisplay);
        userPhone.setText("123-456-7890");

        //set up buttons
        editbtn = getView().findViewById(R.id.editProfile);
        followbtn = getView().findViewById(R.id.followBtn);
        savebtn = getView().findViewById(R.id.saveBtn);
        savebtn.setVisibility(View.INVISIBLE);

        //edit email and phone number
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmail = getView().findViewById(R.id.profileEmail);
                editEmail.setEnabled(true);
                editPhone = getView().findViewById(R.id.phoneNum);
                editPhone.setEnabled(true);

                savebtn.setVisibility(View.VISIBLE);
            }
        });

        //follow the user
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

        //saves the user email and phone number after editing
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = editEmail.getText().toString().trim();
                userEmail.setText(newEmail);

                String newPhone = editPhone.getText().toString().trim();
                userPhone.setText(newPhone);


                /*SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(userPhone, newPhone);
                editor.putString(userEmail, newEmail);
                editor.commit();*/
            }
        });

        return inflater.inflate(R.layout.fragment_user_profile,container, false);
    }


}
