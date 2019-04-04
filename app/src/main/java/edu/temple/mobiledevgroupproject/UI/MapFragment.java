/*MapFragment:
*(1) Receives a list of jobs from parent activity
*(2) Displays mapview with markers using list of jobs
*(3) Displays infowindow that gives job info and allows user to view job details in seperate activity
*(4) Sends data from job from selected markers to parent activity*/

package edu.temple.mobiledevgroupproject.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    //layout objects
    GoogleMap googleMap;
    MapView mapView;
    View view;

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("ON CREATE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = view.findViewById(R.id.map_view);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.getMapAsync(this);
        }

        System.out.println("ON CREATE VIEW");
        return view;
    }

    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        System.out.println("ON VIEW CREATED");
    }*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        System.out.println("ON MAP READY");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
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

}
