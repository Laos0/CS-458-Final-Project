package com.example.finalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

public class MapBox extends Fragment
{
    private MapView mapView;

    public MapBox()
    {
        // Required empty public constructor
    }


    /*@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mapView.onCreateView();
    } */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Mapbox.getInstance(getContext(), "pk.eyJ1Ijoia2FzZXJhZHRrZSIsImEiOiJjanNoeXBkb2owcHpqNDNwa3F6ZXZkZDhnIn0.Do4trV9xyKwtjGagHzL7_Q");
        View view = inflater.inflate(R.layout.fragment_map_box, container,false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback()
                            {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap)
            {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded()
                {
                    @Override
                    public void onStyleLoaded(@NonNull Style style)
                    {
                        // Map is set up and the style has loaded. Now you can add data or make other map adjustments
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();

        mapView.onDestroy();
    }
}
