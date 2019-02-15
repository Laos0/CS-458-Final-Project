package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;
import java.util.Map;

public class MainPage extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Instantiate the activity */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        /* Create our Navigation Drawer as on object */
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        /* Create our toolbar as an object and add a back button to it */
        Toolbar navBar = findViewById(R.id.navBar);
        navBar.setTitle("PictoCache");
        setSupportActionBar(navBar);
        navBar.setNavigationIcon(R.drawable.ic_user_actions);

        /* Open the Navigation Drawer on clicking the hamburger button */
        navBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        final ImageButton weatherButton = findViewById(R.id.weatherBtn);
        weatherButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                GPSTracker locationTracker = new GPSTracker(MainPage.this);

                List<Map<String, String>> data = OpenWeather.retrieveWeather(locationTracker.getLatitude(), locationTracker.getLongitude());

                String title = data.get(0).get("main");
                String desc = data.get(0).get("desc");


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.settings:
                {
                    Intent mainPage = new Intent(MainPage.this, SettingsActivity.class);
                    startActivity(mainPage);
                return true;
            }

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
