package com.example.finalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class HomeFragment extends Fragment
{
    String pathToFile; // path of the image in directory
    FloatingActionButton cameraBtn;
    ImageView photo, filterPhoto;
    Button saveBtn, filterBtn1, filterBtn2, filterBtn3;
    boolean photoExist;
    View filterView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container,false);

        // once the photo is taken
        filterView = inflater.inflate(R.layout.fragment_filter, container,false);



        cameraBtn = view.findViewById(R.id.camera_btn);
        photo = view.findViewById(R.id.photo);


        // for the camera
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Testing to see if take picture button works
                // String tag = "TESTING";
                // Log.d(tag, "DISPATCHPICTURETAKERACTION IS BEING RUN");
                dispatchPictureTakerAction();

            }
        });
        final Button weatherButton = view.findViewById(R.id.weather_btn);
        weatherButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                applyWeatherPopup(v);

            }
        });

        // a boolean is returned from the MainPage
       photoExist = ((MainPage)getActivity()).isThereTargetPhoto();

       return view;
    }

    @Override // after photo is saved, give ImageView an image that is a bitmap
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap bitmap = BitmapFactory.decodeFile(pathToFile);
                photo.setImageBitmap(bitmap);
                ((MainPage)getActivity()).savePhoto(bitmap);
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void applyWeatherPopup(View v){
        GPSTracker locationTracker = new GPSTracker(getContext());

            List<Map<String, String>> data = OpenWeather.retrieveWeather(locationTracker.getLatitude(), locationTracker.getLongitude());


        String title = data.get(0).get("main");
        String desc = data.get(0).get("description");
        String temp = "temperature: " + data.get(1).get("temp");
        String pressure = "pressure: " + data.get(1).get("temp");
        String humidity = "humidity: " + data.get(1).get("temp");
        String temp_low = "Low: " + data.get(1).get("temp");
        String temp_high = "High: " + data.get(1).get("temp");
        String windSpeed = "windspeed: " + data.get(2).get("speed");
        String windChill = "windchill: " +  data.get(2).get("deg");

        LinearLayout viewGroup = v.findViewById(R.id.weatherPup);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getContext().LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.weather_popup, viewGroup);

        final PopupWindow popup = new PopupWindow(getActivity());
        popup.setContentView(layout);
        popup.setWidth(getActivity().getWindow().getWindowManager().getDefaultDisplay().getWidth()*9/10);
        popup.setFocusable(true);
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, 0,0);
        TextView wTitle = layout.findViewById(R.id.weatherTitle);
        wTitle.setText(title);
        TextView wDesc = layout.findViewById(R.id.weatherDesc);
        wDesc.setText(desc);
        TextView wTemp = layout.findViewById(R.id.temp);
        wTemp.setText(temp);
        TextView wPressure = layout.findViewById(R.id.pressure);
        wPressure.setText(pressure);
        TextView wHumidity = (TextView) layout.findViewById(R.id.humidity);
        wHumidity.setText(humidity);
        TextView wTempLow = (TextView) layout.findViewById(R.id.temp_low);
        wTempLow.setText(temp_low);
        TextView wTempHigh = (TextView) layout.findViewById(R.id.temp_high);
        wTempHigh.setText(temp_high);
        TextView wSpeed = (TextView) layout.findViewById(R.id.windSpeed);
        wSpeed.setText(windSpeed);
        TextView wChill = (TextView) layout.findViewById(R.id.windChill);
        wChill.setText(windChill);

    }

    // Intent to capture a photo, load up camera
    private void dispatchPictureTakerAction() {
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        FragmentActivity activity = HomeFragment.this.getActivity();
        PackageManager packageManager = activity.getPackageManager();
        if (takePic.resolveActivity(packageManager) != null) {
            File photoFile = null;
            photoFile = preparePhotoFile();

            savePhotoToDevice(takePic,photoFile);
        }
    }

    // Assuming this method saves the photo file
    private void savePhotoToDevice(Intent takePic, File photoFile){
        if (photoFile != null) {
            pathToFile = photoFile.getAbsolutePath(); // path to the photo file
            Uri photoURI = FileProvider.getUriForFile(HomeFragment.this.getActivity(), "com.example.finalproject", photoFile);
            takePic.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePic, 1);
        }
    }

    // Creating the file of the photo
    private File preparePhotoFile() {
        String pattern = "yyyy-MM-dd";
        String name = new SimpleDateFormat(pattern).format(new Date());
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("myLog", "Excep: " + e.toString());
        }

        return image;
    }


}
