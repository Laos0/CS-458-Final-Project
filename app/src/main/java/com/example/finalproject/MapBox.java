//Created 03-13-2019 by Kase Radtke
//Java file for MapBox fragment to be used in HomeFragment.java

package com.example.finalproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//Import MapBox SDK libraries
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;




public class MapBox extends Fragment {
    private MapView mapView;

    public MapBox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Grabs Kase's API key stored from MapBox.com
        Mapbox.getInstance(getContext(), "pk.eyJ1Ijoia2FzZXJhZHRrZSIsImEiOiJjanNoeXBkb2owcHpqNDNwa3F6ZXZkZDhnIn0.Do4trV9xyKwtjGagHzL7_Q");
        View view = inflater.inflate(R.layout.fragment_map_box, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                //Sets the style for the map. Pulls from style created by Kase stored on mapbox server.
                mapboxMap.setStyle(new Style.Builder().fromUrl("mapbox://styles/kaseradtke/cjshyt7dt0y1r1fs16ej3fr4j"), new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        // Map is set up and the style has loaded. Make any adjustments needed for the maps style here.
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    //Presets for each status change possible with MapBox SDK for best app optimization.
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
    public void onDestroyView() {
        super.onDestroyView();

        mapView.onDestroy();
    }
}
