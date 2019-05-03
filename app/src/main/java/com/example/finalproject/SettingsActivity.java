package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.finalproject.ServerCommunication.SessionManagement;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.widget.Button;

import com.example.finalproject.ServerCommunication.ChangeEmail;
import com.example.finalproject.ServerCommunication.ChangePassword;
import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Spinner spinner;
    private Spinner spinner2;
    private SessionManagement session; // For accessing the current user info
    Button btnChange, emailChange; // Save buttons for changing password and email
    EditText editChange, userEmail; // Changing password and email fields
    Context c;
    AlertDialogManager alert = new AlertDialogManager();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        SharedPreferences prefs = getPreferences(0);
        setTheme(prefs.getInt("theme",R.style.AppTheme));
        super.onCreate(savedInstanceState);
        LanguageSelect.languageSelect(prefs.getInt("LanguageSelection",0),getBaseContext());



        setContentView(R.layout.activity_settings);

        /* Create our toolbar as an object and add a back button to it */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Settings");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        // Go back to the main page on button click
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        // Language Spinner Setup

        spinner = findViewById(R.id.language_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.language_list,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(prefs.getInt("languageSelection",0));
        spinner.setOnItemSelectedListener(this);

        // Themes spinner setup
        spinner2 = findViewById(R.id.themespinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.themeslist,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setSelection(prefs.getInt("themeSelection",0));
        spinner2.setOnItemSelectedListener(this);



        Button applySettings = findViewById(R.id.set_button);
        applySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        ///////////////////////////////////////// Start of Su's settings /////////////////////////////////////////
        c = SettingsActivity.this;
        /*Display displayable user information*/
        // Get the user's info from the session
        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> userInfo = session.getUserDetails();

        // Set the user information to appropriate fields
        userEmail = findViewById(R.id.email_box);

        // Grabs user information from shared preferences
        final String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);

        // Display user information
        userEmail.setText(emailToDisplay);

        // CODE FOR CHANGING PASSWORD -Jordan
        editChange = findViewById(R.id.txt_ChangePass);
        btnChange = findViewById(R.id.btn_ChangePass);

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

        // Allow user to change email
        emailChange = findViewById(R.id.btn_ChangeEmail);
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_email = String.valueOf(emailChange.getText());


                ChangeEmail changeEmail = new ChangeEmail(c);

                try {
                    // Get the result of the login back from the server and check to see if the login was successful
                    String result = changeEmail.execute(userToDisplay, new_email).get();
                    if (result.contains("Success")) {
                        // If successful, then updates email in shared preferences
                        alert.showAlertDialog(c, "Change Completed", result, false);
                        session.editSharedPref("KEY_EMAIL",new_email);
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

        ///////////////////////////////////////// End of Su's settings /////////////////////////////////////////


        // Get the log out button as an object
        Button logout = findViewById(R.id.logout_btn);
        logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Get the session
                SessionManagement session = new SessionManagement(getApplicationContext());

                // Call the log out function in the SessionManagement class
                session.logoutUser();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor editor = getPreferences(0).edit();
        int selectedPosition;
        switch(parent.getId()) {
            case R.id.language_spinner:
                selectedPosition = spinner.getSelectedItemPosition();
                editor.putInt("languageSelection", selectedPosition);
                editor.apply();
                break;
            case R.id.themelabel:
                switch (position){
                    case 0:
                        editor.putInt("theme",R.style.AppTheme);
                        editor.putInt("themeNoAction",R.style.AppTheme_NoActionBar);
                        break;
                    case 1:
                        editor.putInt("theme",R.style.AppThemeDark);
                        editor.putInt("themeNoAction",R.style.AppThemeDark_NoActionBar);
                        break;
                }

                selectedPosition = spinner2.getSelectedItemPosition();
                editor.putInt("themeSelection", selectedPosition);
                editor.apply();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = getPreferences(0).edit();
        int selectedPosition = spinner.getSelectedItemPosition();
        editor.putInt("languageSelection",selectedPosition);
        selectedPosition = spinner2.getSelectedItemPosition();
        editor.putInt("themeSelection", selectedPosition);
        editor.apply();

    }


}


