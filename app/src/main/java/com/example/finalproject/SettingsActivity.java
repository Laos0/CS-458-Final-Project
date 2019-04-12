package com.example.finalproject;

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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Spinner spinner;
    private Spinner spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getPreferences(0);
        LanguageSelect.languageSelect(prefs.getInt("LanguageSelection",0),this);
        setTheme(prefs.getInt("theme",R.style.AppTheme));
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
                LanguageSelect.languageSelect(position,this);
                selectedPosition = spinner.getSelectedItemPosition();
                editor.putInt("languageSelection", selectedPosition);
                editor.apply();
                recreate();
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

