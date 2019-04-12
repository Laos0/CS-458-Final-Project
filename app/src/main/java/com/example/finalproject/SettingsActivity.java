package com.example.finalproject;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.HashMap;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Spinner spinner;
    private Spinner spinner2;
    private SessionManagement session; // For accessing the current user info
    private Dialog thisDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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
        SharedPreferences prefs = getPreferences(0);
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

        /*Display displayable user information*/
        // Get the user's info from the session
        session = new SessionManagement(getApplicationContext());
        final HashMap<String, String> userInfo = session.getUserDetails();

        // Set the user information to appropriate fields
        final EditText userEmail = findViewById(R.id.email_box);
        final EditText userPhone = findViewById(R.id.phone_box);
        final TextView userPassword = findViewById(R.id.password_box);

        // Grabs user information from shared preferences
        String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);
        String phoneToDisplay = userInfo.get(SessionManagement.KEY_PHONE);

        // Display user information
        userEmail.setText(emailToDisplay);
        userPhone.setText(phoneToDisplay);

        /*Allow user to change password*/
        userPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisDialog = new Dialog(SettingsActivity.this);
                thisDialog.setContentView(R.layout.dialog_template);
                final EditText write = thisDialog.findViewById(R.id.write);
                final Button saveBtn = thisDialog.findViewById(R.id.saveBtn);

                write.setEnabled(true);
                saveBtn.setEnabled(true);

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: password change
                    }
                });
                thisDialog.show();
            }
        });

        /*Allow user to change email*/
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisDialog = new Dialog(SettingsActivity.this);
                thisDialog.setContentView(R.layout.dialog_template);
                final EditText write = thisDialog.findViewById(R.id.write);
                final Button saveBtn = thisDialog.findViewById(R.id.saveBtn);

                write.setEnabled(true);
                saveBtn.setEnabled(true);

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputStr = write.getText().toString();
                        session.editSharedPref("KEY_EMAIL",inputStr);
                        thisDialog.cancel();
                    }
                });
                thisDialog.show();
            }
        });

        /*Allow user to change phone number*/
        userPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisDialog = new Dialog(SettingsActivity.this);
                thisDialog.setContentView(R.layout.dialog_template);
                final EditText write = thisDialog.findViewById(R.id.write);
                final Button saveBtn = thisDialog.findViewById(R.id.saveBtn);

                write.setEnabled(true);
                saveBtn.setEnabled(true);

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputStr = write.getText().toString();
                        session.editSharedPref("KEY_PHONE",inputStr);
                        thisDialog.cancel();
                    }
                });
                thisDialog.show();
            }
        });

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
                switch (position) {
                    case 0:
                        Locale locale = new Locale("en");
                        Locale.setDefault(locale);
                        Configuration config = new Configuration();
                        config.locale = locale;
                        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
                        Toast.makeText(this, getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Locale locale2 = new Locale("fr");
                        Locale.setDefault(locale2);
                        Configuration config2 = new Configuration();
                        config2.locale = locale2;
                        getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
                        Toast.makeText(this, getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Locale locale3 = new Locale("es");
                        Locale.setDefault(locale3);
                        Configuration config3 = new Configuration();
                        config3.locale = locale3;
                        getBaseContext().getResources().updateConfiguration(config3, getBaseContext().getResources().getDisplayMetrics());
                        Toast.makeText(this, getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Locale locale4 = new Locale("de");
                        Locale.setDefault(locale4);
                        Configuration config4 = new Configuration();
                        config4.locale = locale4;
                        getBaseContext().getResources().updateConfiguration(config4, getBaseContext().getResources().getDisplayMetrics());
                        Toast.makeText(this, getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Locale locale5 = new Locale("ru");
                        Locale.setDefault(locale5);
                        Configuration config5 = new Configuration();
                        config5.locale = locale5;
                        getBaseContext().getResources().updateConfiguration(config5, getBaseContext().getResources().getDisplayMetrics());
                        break;
                }

                selectedPosition = spinner.getSelectedItemPosition();
                editor.putInt("languageSelection", selectedPosition);
                editor.apply();
                recreate();
                break;
            case R.id.themelabel:
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                }

                selectedPosition = spinner2.getSelectedItemPosition();
                editor.putInt("themeSelection", selectedPosition);
                editor.apply();
                recreate();
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
        editor.apply();

    }
}
