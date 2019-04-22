package com.example.finalproject.ServerCommunication;

import java.util.HashMap;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.finalproject.LoginActivity;

public class SessionManagement
{
    /***** Class Variables *****/
    // Create the shared preferences
    SharedPreferences pref;

    // Editor for the shared preferences
    Editor editor;

    // The context of the session manager
    Context _context;

    // Define the shared pref mode
    int PRIVATE_MODE = 0;

    // Name the shared pref file
    private static final String PREF_NAME = "PictoCachePref";

    // Shared pref keys
    private static final String IS_LOGIN = "LoggedIn";

    // The user name
    public static final String KEY_NAME = "name";

    // User's email address
    public static final String KEY_EMAIL = "email";


    /** Constructor for the Session Manager **/
    public SessionManagement(Context context)
    {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /** Class Functions **/
    // Create a login session
    public void createLoginSession(String name, String email)
    {
        // Store the login value as true
        editor.putBoolean(IS_LOGIN, true);

        // Store the name in preferences
        editor.putString(KEY_NAME, name);

        // Store the email in preferences
        editor.putString(KEY_EMAIL, email);

        // Commit the changes
        editor.commit();
    }

    public void editSharedPref(String key,String input)
    {
        // Store the phone in preferences
        editor.putString(key, input);

        // Commit the changes
        editor.commit();
    }

    // Get the current logged in user data
    public HashMap<String, String> getUserDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();

        // Get the user name and email
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // Return the hash map with the user info
        return user;
    }

    // Check to make sure the user is logged in. If they are not, take them to the login page
    public void checkLogin()
    {
        if(!this.isLoggedIn())
        {
            // If the user is not logged in, take them to the Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Close all other activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Start the login activity
            _context.startActivity(i);
        }
    }

    // Log the user out
    public void logoutUser()
    {
        // Clear all the data from the preferences
        editor.clear();
        editor.commit();

        // After Logging out, go back to the login activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Close all other activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Start the login activity
        _context.startActivity(i);
    }

    // Check to see if logged in
    public boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
