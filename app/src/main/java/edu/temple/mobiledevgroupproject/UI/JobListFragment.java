/*JobListFragment:
*(1) Displays list of potential jobs
*(2) Allows user to sort jobs according to various categories
*(3) Receives updated list of jobs from parent Activity
*(4) Sends job selected from List to parent Activity*/

package edu.temple.mobiledevgroupproject.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.R;

public class JobListFragment extends Fragment {
    ListView jobListView;
    Spinner jobSortSpinner;

    ArrayList<Job> jobList;
    JobSelectedInterface jobSelectedListener;

    public JobListFragment() {
        // Required empty public constructor
    }

    public interface JobSelectedInterface {
        void getSelectedJob(Job selectedJob);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_job_list, container, false);
    }

    //called by parent when user clicks refresh button in actionbar
    public void updateList(ArrayList<Job> jobList) {

    }
}
