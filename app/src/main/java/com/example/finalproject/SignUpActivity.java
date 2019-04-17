package com.example.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.ServerCommunication.BackgroundWorker;

import java.util.concurrent.ExecutionException;

public class SignUpActivity extends AppCompatActivity
{
    EditText userName, password, email, confemail;
    String user, pass, emailAddress, confEmailAddress;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Instantiate the activity and set the layout */
        SharedPreferences prefs = getPreferences(0);
        setTheme(prefs.getInt("theme",R.style.AppTheme));
        super.onCreate(savedInstanceState);
        LanguageSelect.languageSelect(prefs.getInt("LanguageSelection",0),getApplicationContext());

        setContentView(R.layout.activity_sign_up);

        /* Set up the EditText fields */
        userName = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.user_password);
        email = (EditText) findViewById(R.id.user_email);
        confemail = (EditText) findViewById(R.id.conf_user_email);

        /* Create the register button as an object and create an onclick() function */
        Button register = findViewById(R.id.register);

        // Go to the main page on tapping the register button
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Register the user by calling the method
                RegisterUser();
            }
        });

        /* Create our toolbar as an object and add a back button to it */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign Up/Register For PictoCache");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        // Go back to the login page on button click
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

    }

    public void RegisterUser()
    {
        // Get the input from the user
        user = userName.getText().toString().trim();
        pass = password.getText().toString().trim();
        emailAddress = email.getText().toString().trim();
        confEmailAddress = confemail.getText().toString().trim();
        String type = "register";

        // Validate the input before sending the information to the server
        if(user.length() <= 0 || pass.length() <= 0 ||
                emailAddress.length() <= 0 || confEmailAddress.length() <= 0)
        {
            // If the user left a blank field, show an alert
            alert.showAlertDialog(SignUpActivity.this, "Registration Failed", "Please check fields above and retry!", false);
        }
        else if(!emailAddress.equals(confEmailAddress))
        {
            // If the user did not enter the same email in the confirmation, show an alert
            alert.showAlertDialog(SignUpActivity.this, "Registration Failed", "Emails do not match!", false);
        }
        else
        {
            // Create the background worker that will asynchronously send the register request to the server
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            try
            {
                // Get the result of the registration back from the server and check to see if the login was successful
                String result = backgroundWorker.execute(type, user, pass, emailAddress).get();
                if (result.contains("Success"))
                {
                    // Take the user back to the login page to log in using their new credentials
                    Toast.makeText(SignUpActivity.this, "Registration successful!", Toast.LENGTH_SHORT);
                    Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login);
                }
                else
                {
                    alert.showAlertDialog(SignUpActivity.this, "Registration Failed", result, false);
                }

                // Else, an error will be thrown from the BackgroundWorker and be displayed to the user
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
