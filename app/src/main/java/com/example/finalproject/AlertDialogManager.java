package com.example.finalproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager
{
    /* Function to show a simple Alert Dialog */
    public void showAlertDialog(Context context, String title, String message, Boolean status)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Set the dialog title
        alertDialog.setTitle(title);

        // Set the dialog message
        alertDialog.setMessage(message);

        // Set the dialog icon
        if(status != null)
            alertDialog.setIcon((status) ? R.drawable.ic_success : R.drawable.ic_failure);

        // Set the OK button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        // Show the alert message
        alertDialog.show();
    }
}
