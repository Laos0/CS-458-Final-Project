package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainPage extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Instantiate the activity */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        /* Create our toolbar as an object and add a back button to it */
        Toolbar navBar = (Toolbar) findViewById(R.id.navBar);
        navBar.setTitle("PictoCache");
        setSupportActionBar(navBar);
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
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.userActions:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
