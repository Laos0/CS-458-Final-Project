package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Creates the activity upon starting the app */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

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
                Intent signUp = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUp);
                break;
            }
            case R.id.log_in: {
                Intent mainPage = new Intent(LoginActivity.this, MainPage.class);
                startActivity(mainPage);
                break;
            }

            default:
                break;
        }
    }

}
