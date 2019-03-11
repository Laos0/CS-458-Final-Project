package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class UserProfileFragment extends Fragment
{
    //private EditText userName;
    Button editbtn;
    Button followbtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        /*SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Username",userName.getText().toString());
        editor.commit();


        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        userName.setText(settings.getString("Username", "").toString());
        */

        editbtn = getView().findViewById(R.id.editProfile);
        followbtn = getView().findViewById(R.id.followBtn);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


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


        return inflater.inflate(R.layout.fragment_user_profile,container, false);
    }


}
