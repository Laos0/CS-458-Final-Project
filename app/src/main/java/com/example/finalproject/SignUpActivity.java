package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity
{
    EditText userName, password, email, confemail;
    SignUpDatabase dbTester;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        /* Instantiate the activity and set the layout */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        /* Create the register button as an object and create an onclick() function */
        Button register = findViewById(R.id.register);

        // Go to the main page on tapping the register button
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent mainPage = new Intent(SignUpActivity.this, MainPage.class);
                startActivity(mainPage);
            }
        });


        /* Create our toolbar as an object and add a back button to it */
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign Up/Register For PictoCache");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        // Go back to the login page on button click
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });



        userName= findViewById(R.id.userID);
        password= findViewById(R.id.editText2);
        email= findViewById(R.id.editText3);
        confemail= findViewById(R.id.editText4);

        dbTester = new SignUpDatabase(this);
    }

}
