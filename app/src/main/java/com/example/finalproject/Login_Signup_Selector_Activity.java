package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finalproject.ServerCommunication.BackgroundWorker;
import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.concurrent.ExecutionException;

public class Login_Signup_Selector_Activity extends AppCompatActivity implements View.OnClickListener
{
    // Global Variables
    SessionManagement session;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Creates the activity upon starting the app */
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getPreferences(0);
        LanguageSelect.languageSelect(prefs.getInt("LanguageSelection",0),this);
        setTheme(prefs.getInt("theme",R.style.AppTheme));
        setContentView(R.layout.login_signup_selector_layout);

        /* Create a session manager */
        session = new SessionManagement(getApplicationContext());



        /* Buttons */
        // Create our sign-up and login buttons as an objects
        Button signUp = findViewById(R.id.sign_up);
        Button login = findViewById(R.id.log_in);

        // Create an onclick listener for our sign-up button to take the user to the sign up page on button click
        signUp.setOnClickListener(this);

        // Create an onclick listener for the login button to take the user to the main page
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // Determine which button was pressed and open the corresponding page
        switch (v.getId())
        {
            case R.id.sign_up: {
                Intent signUp = new Intent(Login_Signup_Selector_Activity.this, SignUpActivity.class);
                startActivity(signUp);
                break;
            }
            case R.id.log_in: {
                Intent signUp = new Intent(Login_Signup_Selector_Activity.this, LoginActivity.class);
                startActivity(signUp);
                break;
            }

            default:
                break;
        }
    }
}
