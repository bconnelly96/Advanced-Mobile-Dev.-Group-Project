/*FormFragment:
*(1) Displays fields for Job data retrieval
*(2) Gets Posted Job data from user
*(3) Hands valid Job data to parent Activity*/

package edu.temple.mobiledevgroupproject.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.temple.mobiledevgroupproject.BackEnd.Job;
import edu.temple.mobiledevgroupproject.R;

public class FormFragment extends Fragment {
    //layout objects


    FormInterface formInterface;

    public FormFragment() {
        // Required empty public constructor
    }

    public interface FormInterface {
        void getDataFromForm(Job jobData);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

}
