package com.example.finalproject.ServerCommunication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.finalproject.LoginActivity;

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

public class BackgroundWorker extends AsyncTask<String, Void, String>
{
    // Global Variables
    Context context;
    AlertDialog alertDialog;
    private ProgressDialog pDialog;

    // Constructor
    public BackgroundWorker(Context ctx)
    {
        context = ctx;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();

        // Test function to make sure that the user is not currently logged in
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");

        // Showing progress dialog
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Logging in...");
        pDialog.setCancelable(true);
        pDialog.show();

    }

    // Will run in the background without being directly called
    @Override
    protected String doInBackground(String... params)
    {
        // Get the type of function that the background worker needs to do
        String type = params[0];


        // If we are trying to login to the application, do the following code
        if(type.equals("login"))
        {
            // Get the username and password
            String username = params[1];
            String password = params[2];

            // Get the url to the database that we are logging into
            String login_url = "http://144.13.22.48/CS458SP19/Team2/api/EmailUserLogin.php";

            try
            {
                // Get the URL for the database we are trying to connect to and open an HTTP connection to the database
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Create an output stream to send our data to the database to check the login info against
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                // This is the data that we received from the user that we will be passing
                String post_data = URLEncoder.encode("username", "UTF-8")+"="+
                                   URLEncoder.encode(username, "UTF-8")+"&"+
                                   URLEncoder.encode("password", "UTF-8")+"="+
                                   URLEncoder.encode(password, "UTF-8");

                // Write the post data to the buffered writer and close the writer and the output stream
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // Create an input stream to get the result of the login back from the database
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                // Read each line of the data we received and store it the result string
                while((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }

                // Once we have finished getting all the data and storing it to result, close the buffered reader, input stream, and HTTP connection
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                // Return the result
                return result;
            } catch (MalformedURLException e)
            {
                alertDialog.setMessage("Incorrect URL!");
                e.printStackTrace();
            }
            catch (IOException e)
            {
                alertDialog.setMessage("IO Exception!");
                e.printStackTrace();
            }
        }

        // If the type is 'register', perform the following code
        else if(type.equals("register"))
        {
            // Get the url to the database that we are trying to add a user into
            String signup_url = "http://144.13.22.48/CS458SP19/Team2/api/signup.php";

            // Get the username password
            String username = params[1];
            String password = params[2];
            String email = params[3];

            try
            {
                // Get the URL for the database we are trying to connect to and open an HTTP connection to the database
                URL url = new URL(signup_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                // Create an output stream to send our data to the database to check the login info against
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                // This is the data that we received from the user that we will be passing
                String post_data = URLEncoder.encode("username", "UTF-8")+"="+
                        URLEncoder.encode(username, "UTF-8")+"&"+
                        URLEncoder.encode("password", "UTF-8")+"="+
                        URLEncoder.encode(password, "UTF-8")+"&"+
                        URLEncoder.encode("email", "UTF-8")+"="+
                        URLEncoder.encode(email, "UTF-8");

                // Write the post data to the buffered writer and close the writer and the output stream
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // Create an input stream to get the result of the login back from the database
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";

                // Read each line of the data we received and store it the result string
                while((line = bufferedReader.readLine()) != null)
                {
                    result += line;
                }

                // Once we have finished getting all the data and storing it to result, close the buffered reader, input stream, and HTTP connection
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                // Return the result
                return result;

            } catch (MalformedURLException e)
            {
                alertDialog.setMessage("Incorrect URL!");
                e.printStackTrace();
            }
            catch (IOException e)
            {
                alertDialog.setMessage("IO Exception!");
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) { }

    @Override
    protected void onProgressUpdate(Void... values)
    {
        super.onProgressUpdate(values);
    }
}
