package com.example.finalproject;

import android.util.JsonReader;
import android.util.JsonToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class WeatherParser {

    private static final HashMap<String, String> openWeatherData = new HashMap<>();
    static {
        openWeatherData.put("weather", null);
        openWeatherData.put("main", null);
        openWeatherData.put("wind", null);
        openWeatherData.put("sys", null);
        openWeatherData.put("name", null);
    }

    private static final HashMap<String, String> weatherData = new HashMap<>();
    static {
        weatherData.put("main", null);
        weatherData.put("description", null);
        weatherData.put("icon", null);
    }

    private static final HashMap<String, String> mainData = new HashMap<>();
    static {
        mainData.put("temp", null);
        mainData.put("pressure", null);
        mainData.put("humidity", null);
        mainData.put("temp_min", null);
        mainData.put("temp_max", null);
    }

    private static final HashMap<String, String> windData = new HashMap<>();
    static {
        windData.put("speed", null);
        windData.put("deg", null);
    }

    private static final HashMap<String, String> sysData = new HashMap<>();
    static {
        sysData.put("sunrise", null);
        sysData.put("sunset", null);
    }

    static List<Map<String, String>> parse(final InputStream in) throws IOException {
        try {

            final JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            readWeather(reader);

            List<Map<String, String>> data = new ArrayList<>();
            data.add(openWeatherData);
            data.add(weatherData);
            data.add(mainData);
            data.add(windData);
            data.add(sysData);

            return data;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            in.close();
        }
    }

    // When we find an object we need, put value in the map.
    // Otherwise, skip it.
    private static void readWeather(final JsonReader reader) throws IOException {

        reader.beginObject();

        while (reader.hasNext()) {

            String name = reader.nextName();

            if (openWeatherData.containsKey(name) && !valueIsNull(reader)) {

                switch (name) {
                    case "name":
                        openWeatherData.put(name, reader.nextString());
                        break;
                    case "weather":
                        reader.beginArray();

                        while (reader.hasNext()) {
                            reader.beginObject();

                            while (reader.hasNext()) {
                                name = reader.nextName();
                                if (weatherData.containsKey(name) && !valueIsNull(reader)) {
                                    weatherData.put(name, reader.nextString());
                                } else {
                                    reader.skipValue();
                                }
                            }

                            reader.endObject();
                        }

                        reader.endArray();
                        break;
                    default:
                        reader.beginObject();

                        while (reader.hasNext()) {
                            name = reader.nextName();

                            if (valueIsNull(reader)) {
                                reader.skipValue();
                            } else if (mainData.containsKey(name)) {
                                mainData.put(name, reader.nextString());
                            } else if (windData.containsKey(name)) {
                                windData.put(name, reader.nextString());
                            } else if (sysData.containsKey(name)) {
                                sysData.put(name, reader.nextString());
                            } else {
                                reader.skipValue();
                            }
                        }

                        reader.endObject();
                        break;
                }
            } else {
                reader.skipValue();
            }

        }

        reader.endObject();
    }

    private static boolean valueIsNull(JsonReader reader) throws IOException {
        return reader.peek() == JsonToken.NULL;
    }

}
