package com.example.finalproject;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;

import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

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
        Spinner spinner = findViewById(R.id.language_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.language_list,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
       switch (position){
           case 0:
               Locale locale = new Locale("en");
               Locale.setDefault(locale);
               Configuration config = new Configuration();
               config.locale = locale;
               getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
               Toast.makeText(this,getString(R.string.enIsMyfriend),Toast.LENGTH_LONG).show();
               break;
           case 1:
               Locale locale2 = new Locale("fr");
               Locale.setDefault(locale2);
               Configuration config2 = new Configuration();
               config2.locale = locale2;
               getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
               Toast.makeText(this,getString(R.string.enIsMyfriend),Toast.LENGTH_LONG).show();
               break;
           case 2:
               Locale locale3 = new Locale("es");
               Locale.setDefault(locale3);
               Configuration config3 = new Configuration();
               config3.locale = locale3;
               getBaseContext().getResources().updateConfiguration(config3,getBaseContext().getResources().getDisplayMetrics());
               Toast.makeText(this,getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
               break;
           case 3:
               Locale locale4 = new Locale("de");
               Locale.setDefault(locale4);
               Configuration config4 = new Configuration();
               config4.locale = locale4;
               getBaseContext().getResources().updateConfiguration(config4,getBaseContext().getResources().getDisplayMetrics());
               Toast.makeText(this,getString(R.string.enIsMyfriend), Toast.LENGTH_LONG).show();
               break;
           case 4:
               Locale locale5 = new Locale("ru");
               Locale.setDefault(locale5);
               Configuration config5 = new Configuration();
               config5.locale = locale5;
               getBaseContext().getResources().updateConfiguration(config5,getBaseContext().getResources().getDisplayMetrics());
               break;
       }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {




    }
}
