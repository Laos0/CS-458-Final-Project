package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalproject.ServerCommunication.BackgroundWorker;
import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    // Global Variables
    EditText txtUsername, txtPassword;
    SessionManagement session;
    AlertDialogManager alert = new AlertDialogManager();
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Creates the activity upon starting the app */
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getPreferences(0);

        setTheme(prefs.getInt("theme",R.style.AppTheme));
        position = prefs.getInt("languageSelection",0);
        switch (position) {
            case 0:
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 1:
                Locale locale2 = new Locale("fr");
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 2:
                Locale locale3 = new Locale("es");
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config3.locale = locale3;
                getBaseContext().getResources().updateConfiguration(config3, getBaseContext().getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 3:
                Locale locale4 = new Locale("de");
                Locale.setDefault(locale4);
                Configuration config4 = new Configuration();
                config4.locale = locale4;
                getBaseContext().getResources().updateConfiguration(config4, getBaseContext().getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
            case 4:
                Locale locale5 = new Locale("ru");
                Locale.setDefault(locale5);
                Configuration config5 = new Configuration();
                config5.locale = locale5;
                getBaseContext().getResources().updateConfiguration(config5, getBaseContext().getResources().getDisplayMetrics());
                //Toast.makeText(context, context.getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                break;
        }

        setContentView(R.layout.login_screen);

        /* Create a session manager */
        session = new SessionManagement(getApplicationContext());

        /* Get the edit text fields */
        txtUsername = findViewById(R.id.userID);
        txtPassword = findViewById(R.id.password);

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
                //LogIn();
                Intent home = new Intent(LoginActivity.this, SettingsActivity.class);
                startActivity(home);
                break;
            }

            default:
                break;
        }
    }

    // Function to log the user into the application
    void LogIn()

    {
        // Get the username and password from the fields
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String type = "login";
        // Check to make sure the user actually entered a username and password
        if(username.trim().length() < 0 && password.trim().length() < 0)
        {
            // If the user didn't enter in anything, show an alert
            alert.showAlertDialog(LoginActivity.this, "Login Failed", "No username or password entered!", false);
        }

        // Else, send the user's input to the server to see if their account exists in our database
        else if(username.trim().length() > 0 && password.trim().length() > 0)
        {
            // Create the background worker that will asynchronously send the login request to the server
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            try
            {
                // Get the result of the login back from the server and check to see if the login was successful
                String result = backgroundWorker.execute(type, username, password).get();
                if (result.contains("Success"))
                {
                    // Create the user login session
                    session.createLoginSession(username, username + "@gmail.com");
                    Intent home = new Intent(LoginActivity.this, MainPage.class);
                    startActivity(home);
                }
                else
                {
                    alert.showAlertDialog(LoginActivity.this, "Login Failed", result, false);
                }

                // Else, an error will be thrown from the BackgroundWorker and be displayed to the user
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getPreferences(0);
        if(position != prefs.getInt("languageSelection",0)){
            recreate();
        }
    }

}

