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
    MapView mapView;
    View mView;

    Context context;
    ArrayList<Job> jobsToDisplay;
    JobSelectedInterface jobSelectedListener;

    public MapFragment() {
        // Required empty public constructor
    }

    public interface JobSelectedInterface {
        void jobSelected(Job selectedJob);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        Bundle args = getArguments();
        if (args != null) {
            jobsToDisplay = (ArrayList<Job>) args.getSerializable("JOBS_TO_DISPLAY");
        }
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = mView.findViewById(R.id.map_view);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        configureMap(googleMap, this.jobsToDisplay);
    }

    public void configureMap(GoogleMap googleMap, ArrayList<Job> jobsToDisplay) {
        MapsInitializer.initialize(context);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        for (int i = 0; i < jobsToDisplay.size(); i++) {
            //dummy markers
            googleMap.addMarker(new MarkerOptions()
            .position(new LatLng(40.7128, 74.0060))
            .title("dummy title")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

        CameraPosition camPos = CameraPosition.builder()
                //dummy target
                .target(new LatLng(40.7128, 74.0060))
                .zoom(15)
                .bearing(0)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(camPos));
    }

    //called when user clicks refresh button in actionbar
    public void updatedJobList(ArrayList<Job> updatedJobsToDisplay) {
        jobsToDisplay = updatedJobsToDisplay;
    }
}
