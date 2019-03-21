/*MapFragment:
*(1) Receives a list of jobs from parent activity
*(2) Displays mapview with markers using list of jobs
*(3) Displays infowindow that gives job info and allows user to view job details in seperate activity
*(4) Sends data from job from selected markers to parent activity*/

package edu.temple.mobiledevgroupproject.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;

import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.R;

public class MapFragment extends Fragment {
    //layout objects
    MapView mapView;
    View mView;

    Context mContext;
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
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    //called when user clicks refresh button in actionbar
    public void updatedJobList(ArrayList<Job> updatedJobsToDisplay) {

    }

}
