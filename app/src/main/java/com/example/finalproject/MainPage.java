package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.HashMap;

public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DrawerLayout drawer; // for the drawer menu
    private SessionManagement session; // For accessing the current user info
    private Bitmap targetPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Instantiate the activity */
        SharedPreferences prefs = getPreferences(0);
        setTheme(prefs.getInt("themeNoAction",R.style.AppTheme_NoActionBar));
        super.onCreate(savedInstanceState);
        LanguageSelect.languageSelect(prefs.getInt("LanguageSelection",0),getBaseContext());

        setContentView(R.layout.activity_main_page);


        // ----------------------- Navigation Drawer Implementations ---------------------------------------------------------------
        // The tool bar or navigation to add friend implementations
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Sets title up toolbar
        getSupportActionBar().setTitle("HotSpot");



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // This line of code stops all other activities, a goal must be met inorder to proceed
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /* This code below loads the add friend fragment */

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Get the user's info from the session
        session = new SessionManagement(getApplicationContext());
        HashMap<String, String> userInfo = session.getUserDetails();

        // Set the user information in the Navigation Drawer header
        View headerView = navigationView.getHeaderView(0); // Needed to edit the nav header
        TextView userName = headerView.findViewById(R.id.displayUserName);
        TextView userEmail = headerView.findViewById(R.id.displayEmail);

        String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);

        userName.setText(userToDisplay);
        userEmail.setText(emailToDisplay);

        //--------------------------- End of Navigation Drawer Implementations ----------------------------------------------------

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
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // ------------------ Sony's Navigation Drawer Methods -----------------------------------------------------
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch(menuItem.getItemId())
        {
            case R.id.nav_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new AddFragment()).commit();
                break;
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new UserProfileFragment()).commit();
                break;
            case R.id.nav_friends:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new FriendFragment()).commit();
                break;
            case R.id.nav_gallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new GalleryFragment()).commit();
                break;
            case R.id.nav_settings:
                Intent settingsPage = new Intent(MainPage.this, SettingsActivity.class);
                startActivity(settingsPage);
                break;
            case R.id.nav_email:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new ContactUsFragment()).commit();
                break;
            case R.id.nav_notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                        new NotificationFragment()).commit();
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



    // ------------------- Sony's Methods for data on Fragments -----------------------------------------------------

    // Grabbing the photo bitmap from HomeFragment from the recent taken photo
    public void savePhoto(Bitmap photo){
        targetPhoto = photo;
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,
                new FilterFragment()).commit();
        getSupportActionBar().hide();
    }

    public boolean isThereTargetPhoto(){
        return targetPhoto != null;
    }

    public Bitmap getTargetPhoto(){
        return targetPhoto;
    }

    // ---------------------- End of Sony's Methods for data on Fragments ------------------------------------------
}
