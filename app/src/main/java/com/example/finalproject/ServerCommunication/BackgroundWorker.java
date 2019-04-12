package com.example.finalproject.ServerCommunication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    // Global Variables
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private AlertDialog alertDialog;

    // Constructor
    public BackgroundWorker(Context ctx) {
        context = ctx;
    }

    // Will run in the background without being directly called
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {
        // Get the username, password, and type of function that the background worker needs to do
        String type = params[0];
        String username = params[1];
        String password = params[2];

        // Get the url to the database that we are logging into
        String login_url = "http://144.13.22.48/CS458SP19/Team2/api/testEmailLogin.php";
        String changePassword_url = "http://144.13.12.48/CS458SP19/Team2/api/changePassword.php";

        // If we are trying to login to the application, do the following code
        if (type.equals("login")) {
            try {
                // Get the URL for the database we are trying to connect to and open an HTTP connection to the database
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Create an output stream to send our data to the database to check the login info against
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                // This is the data that we received from the user that we will be passing
                String post_data = URLEncoder.encode("username", "UTF-8") + "=" +
                        URLEncoder.encode(username, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" +
                        URLEncoder.encode(password, "UTF-8");

                // Write the post data to the buffered writer and close the writer and the output stream
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // Create an input stream to get the result of the login back from the database
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.ISO_8859_1));
                StringBuilder result = new StringBuilder();
                String line;

                // Read each line of the data we received and store it the result string
                while ((line = bufferedReader.readLine()) != null) {
                    result.append(line);
                }

                // Once we have finished getting all the data and storing it to result, close the buffered reader, input stream, and HTTP connection
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                // Return the result
                return result.toString();
            } catch (MalformedURLException e) {
                alertDialog.setMessage("Incorrect URL!");
                e.printStackTrace();
            } catch (IOException e) {
                alertDialog.setMessage("IO Exception!");
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        // Test function to make sure that the user is not currently logged in
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
