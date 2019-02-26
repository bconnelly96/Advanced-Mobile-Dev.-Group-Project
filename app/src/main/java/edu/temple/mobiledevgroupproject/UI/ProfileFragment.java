/*ProfileFragment:
*(1) Receives User from parent activity
*(2) Sets and Displays its UI elements using User object received*/

package edu.temple.mobiledevgroupproject.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import edu.temple.mobiledevgroupproject.BackEnd.User;
import edu.temple.mobiledevgroupproject.R;

public class ProfileFragment extends Fragment {
    //layout objects
    private TextView userNameView;
    private TextView nameView;
    private TextView ageView;
    private RatingBar userRatingView;

    User user;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
