package com.example.finalproject;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.List;
import java.util.Map;

public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawer; // for the drawer menu

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Instantiate the activity */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Create our Navigation Drawer as on object
        /*
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        // Create our toolbar as an object and add a back button to it
        Toolbar navBar = findViewById(R.id.navBar);
        navBar.setTitle("PictoCache");
        setSupportActionBar(navBar);
        navBar.setNavigationIcon(R.drawable.ic_user_actions);

        // Open the Navigation Drawer on clicking the hamburger button
        navBar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        */
        // ----------------------- Navigation Drawer Implementations ---------------------------------------------------------------
        // The tool bar or navigation to add friend implementations
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // This line of code stops all other activities, a goal must be met inorder to proceed
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // This code below loads the add friend fragment

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //--------------------------- End of Navigation Drawer Implementations ----------------------------------------------------
        final ImageButton weatherButton = findViewById(R.id.weatherBtn);
        weatherButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                GPSTracker locationTracker = new GPSTracker(MainPage.this);

                List<Map<String, String>> data = OpenWeather.retrieveWeather(locationTracker.getLatitude(), locationTracker.getLongitude());

                String title = data.get(0).get("main");
                String desc = data.get(0).get("description");
                String temp = data.get(1).get("temp");
                String pressure = data.get(1).get("temp");
                String humidity = data.get(1).get("temp");
                String temp_low = data.get(1).get("temp");
                String temp_high = data.get(1).get("temp");
                String windSpeed = data.get(2).get("speed");
                String windChill = data.get(2).get("deg");

                LinearLayout viewGroup = (LinearLayout) MainPage.this.findViewById(R.id.weatherPup);
                LayoutInflater layoutInflater = (LayoutInflater) MainPage.this.getSystemService(MainPage.this.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.weather_popup, viewGroup);

                final PopupWindow popup = new PopupWindow(MainPage.this);
                popup.setContentView(layout);
                popup.setWidth(MainPage.this.getWindow().getWindowManager().getDefaultDisplay().getWidth()*9/10);
                popup.setFocusable(true);
                popup.showAtLocation(layout, Gravity.NO_GRAVITY, 0,0);





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

    // ------------------ Sony's Navigation Drawer Methods -----------------------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()){
            case R.id.nav_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new AddFragment()).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_friends:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new FriendFragment()).commit();
                break;
            case R.id.nav_camera:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new CameraFragment()).commit();
                break;
        }

        drawer.closeDrawer((GravityCompat.START));
        // false means no items will be selected
        return true;
    }

    @Override
    public void onBackPressed() {
        // after pressing the back key, instead of leaving the menu, we just close it
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    // ----------------------- End of Sony's Navigation Drawer Methods -------------------------------------------
}
