package edu.temple.mobiledevgroupproject.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import edu.temple.mobiledevgroupproject.R;

public class JobListFragment extends Fragment {
    ListView jobList;
    Spinner jobSortSpinner;

    public JobListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_list, container, false);
    }

}
