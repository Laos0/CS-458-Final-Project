package com.example.finalproject;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.finalproject.ServerCommunication.ChangePassword;
import com.example.finalproject.ServerCommunication.SessionManagement;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class UserProfileFragment extends Fragment
{
    //private EditText userName;
    //Button editbtn;
    Button followbtn, savebtn, btnChange;
    EditText editChange;
    Context c;
    AlertDialogManager alert = new AlertDialogManager();
    private DrawerLayout drawer; // for the drawer menu
    private SessionManagement session; // For accessing the current user info


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        c = this.getContext();

        // Get the user's info from the session
        session = new SessionManagement(getActivity().getApplicationContext());
        HashMap<String, String> userInfo = session.getUserDetails();

        // Set the user information
        final TextView userName = getView().findViewById(R.id.profileUserName);
        TextView userEmail = getView().findViewById(R.id.profileEmail);

        final String userToDisplay = userInfo.get(SessionManagement.KEY_NAME);
        String emailToDisplay = userInfo.get(SessionManagement.KEY_EMAIL);

        userName.setText(userToDisplay);
        userEmail.setText(emailToDisplay);

        //editbtn = getView().findViewById(R.id.editProfile);
        followbtn = getView().findViewById(R.id.followBtn);
        //savebtn = getView().findViewById(R.id.saveBtn);
        //savebtn.setVisibility(View.INVISIBLE);

        /*editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editEmail = (EditText) getView().findViewById(R.id.profileEmail);
                editEmail.setEnabled(true);
                EditText editPhone = (EditText) getView().findViewById(R.id.phoneNum);
                editPhone.setEnabled(true);

                savebtn.setVisibility(View.VISIBLE);
            }
        });*/

        //follow the user
        followbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followbtn.getText().toString().equalsIgnoreCase("Follow me")) {
                    followbtn.setText("Following");
                } else {
                    followbtn.setText("Follow me");
                }
            }
        });

        /*//saves the user email and phone number
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.getText().toString().trim();

                //userName.setText();
            }
        });
*/
        // CODE FOR CHANGING PASSWORD -Jordan
        editChange = getView().findViewById(R.id.txt_ChangePass);
        btnChange = getView().findViewById(R.id.btn_ChangePass);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_pass = String.valueOf(editChange.getText());
                ChangePassword changePassword = new ChangePassword(c);

                try {
                    // Get the result of the login back from the server and check to see if the login was successful
                    String result = changePassword.execute(userToDisplay, new_pass).get();
                    if (result.contains("Success")) {
                        alert.showAlertDialog(c, "Change Completed", result, false);
                    } else {
                        alert.showAlertDialog(c, "Change Failed", result, false);
                    }

                    // Else, an error will be thrown from the BackgroundWorker and be displayed to the user
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return inflater.inflate(R.layout.fragment_user_profile,container, false);
    }


}
