package com.example.finalproject;

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
    private static final String APP_ID = "a13ad27bb93bad8492322d46f328666e";

    public static List<Map<String, String>> retrieveWeather(final String zipCode) {

        final List<Map<String, String>> data = new ArrayList<>();

        // Build URL
        final String requestURL = createRequestURL(zipCode);

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

    private static String createRequestURL(final String  cityID) {
        return URL + "?id=" + cityID + "&units=imperial&appid=" + APP_ID;
    }

}
