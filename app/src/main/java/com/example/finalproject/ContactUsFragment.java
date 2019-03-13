package com.example.finalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactUsFragment extends Fragment {

    private EditText editTextTo;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button sendBtn;
    private Handler myHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contactus, container,false);

        // For later use
        myHandler = new Handler();

        editTextTo = (EditText) view.findViewById(R.id.edit_text_to);
        editTextSubject = (EditText) view.findViewById(R.id.edit_text_subject);
        editTextMessage = (EditText) view.findViewById(R.id.edit_text_message);
        editTextTo.setText("laos0524@my.uwstout.edu");

        sendBtn = (Button) view.findViewById(R.id.button_sendEmail);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        return view;
    }

    public void sendMail(){

        String recipients = editTextTo.getText().toString();
        // finds the "," within the recipients if any, and put them in a string array
        String[] recipientList = recipients.split(",");

        // grabbing the user's input and turning it to a string
        String subject = editTextSubject.getText().toString();
        String message = editTextMessage.getText().toString();


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipientList);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);


        // opening email clients like gmail etc.
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"Choose an email client"));
        // To wait for an intent
        startActivityForResult(intent, 1);

    }

    void toastEmailSend(){
        Toast.makeText(getActivity(), "Your email has been sent.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //after the email is sent, return to the home page fragment
        getFragmentManager().beginTransaction().replace(R.id.frame_container,
                new HomeFragment()).commit();
        if(requestCode == 1){
            toastEmailSend();
        }else{

        }
    }
}
