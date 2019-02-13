package com.example.finalproject;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OpenWeather {

    private static final String URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String APP_ID = "82c47aa6e7dddfb4ca74723c654b3d41";

    public static List<Map<String, String>> retrieveWeather(double lat, double lon) {

        final List<Map<String, String>> data = new ArrayList<>();

        // Build URL
        final String requestURL = createRequestURL(lat, lon);

        // Send API Request
        try {
            final URL url = new URL(requestURL);
            final InputStream inputStream = getStream(url);

            // Parse Response
            data.addAll(WeatherParser.parse(inputStream));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }

    private static InputStream getStream(final URL url) throws IOException {
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();

        return conn.getInputStream();
    }

    private static String createRequestURL(final double lat, final double lon) {
        return URL + "?lat=" + lat + "&lon=" + lon + "&units=imperial&appid=" + APP_ID;
    }

}