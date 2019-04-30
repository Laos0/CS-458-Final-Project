/**** CLASS SUMMARY *****
    The purpose of this class is to add friend through the fragment_addfriend.xml.
    The whole class is currently in development
    The class DOES NOT access the database to check if a friend exist or not.
 */
package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AddFragment extends Fragment {

    EditText userAcc;
    Button submitBtn;
    TextView dummyT; // variable purpose: Testing
    boolean isFriendExist;
    int counter; // this is a hardcoded variable to increment the friend's id, starting at 4

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container,false);

        counter = 4;

        // Setting the views to the global variables
        userAcc = view.findViewById(R.id.friendAcc);
        submitBtn = view.findViewById(R.id.submitAdd);
        dummyT = view.findViewById(R.id.dummyText);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Testing code line, to see if my button was working in fragment
                dummyT.setText(userAcc.getText());
                isFriendExist = doesFriendExist(userAcc.getText().toString()); // check if friend searched exists

                // get the text from the editTextView
                String name = userAcc.getText().toString();
                // End of transferring data to FriendFragment

                if(isFriendExist != true){
                    Log.i("Does Friend Exist: ", "Yes");
                }else{
                    Log.i("Does Friend Exist: ", "No");
                }


                //TODO:
                // In this setOnClickListener, it will search for the friend's name in the database
                // If the friend exist, there will be a friend request to that friend
                // The friend will ither accept or decline the request and returns a 0 or 1
                // The 0 represent false, and 1 true
                // If friend accepts the friend request then his account will be under the requested user's friend list

            }
        });

        return view;
    }

    /*
       Check if friend exist before adding a friend
       Will need modification to match database
     */
    public boolean doesFriendExist(String name){

        // ------ All of this is on the server side
        // server has tables: rows columns, it does not have an arraylist
        // Mock Data
        ArrayList<String> dataNames;
        dataNames = new ArrayList<String>();

        // storing Bobs into the dataName array
        for(int i = 0; i < 50; i++){
            dataNames.add("Bob" + i);
        }

        // Searching for friend in the mocked data
        // O(n) - time complexity - worst case scenario
        // Real scenario - we will use mysql call -- note: Back end already did this method

        for(int j = 0; j < dataNames.size(); j++){
            if(name.equals(dataNames.get(j))){
                return true;
            }
        }

        return false;
        // ------- end of server side
    }
}
