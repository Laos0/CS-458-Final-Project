package com.example.finalproject;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

class WeatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_popup);
        AsyncTask<Double, Void, List<Map<String, String>>> task = new WeatherTask(this);
        task.execute(45.5,23.6);





    }

    private static class WeatherTask extends AsyncTask<Double, Void, List<Map<String, String>>> {
        private WeakReference<WeatherActivity> activityReference;

        WeatherTask(WeatherActivity context) {
            this.activityReference = new WeakReference<>(context);
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected List<Map<String, String>> doInBackground(Double... values) {


            List<Map<String, String>> data = OpenWeather.retrieveWeather(values[0], values[1]);


            return data;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Map<String, String>> data) {
            super.onPostExecute(data);
            TextView title = activityReference.get().findViewById(R.id.weatherTitle);
            title.setText(data.get(0).get("main"));
            TextView desc = activityReference.get().findViewById(R.id.weatherDesc);
            desc.setText(data.get(0).get("description"));
            TextView temp = activityReference.get().findViewById(R.id.temp);
            String tempString = "temperature: " + data.get(1).get("temp");
            temp.setText(tempString);
            TextView pres = activityReference.get().findViewById(R.id.pressure);
            String pressure = "pressure: " + data.get(1).get("temp");
            pres.setText(pressure);
            TextView hum = activityReference.get().findViewById(R.id.humidity);
            String humidity = "humidity: " + data.get(1).get("temp");
            hum.setText(humidity);
            TextView tempL = activityReference.get().findViewById(R.id.temp_low);
            String temp_low = "Low: " + data.get(1).get("temp");
            tempL.setText(temp_low);
            TextView tempH = activityReference.get().findViewById(R.id.temp_high);
            String temp_high = "High: " + data.get(1).get("temp");
            tempH.setText(temp_high);
            TextView windS = activityReference.get().findViewById(R.id.windSpeed);
            String windSpeed = "windspeed: " + data.get(2).get("speed");
            windS.setText(windSpeed);
            TextView windC = activityReference.get().findViewById(R.id.windChill);
            String windChill = "windchill: " + data.get(2).get("deg");
            windC.setText(windChill);


        }
    }
}
