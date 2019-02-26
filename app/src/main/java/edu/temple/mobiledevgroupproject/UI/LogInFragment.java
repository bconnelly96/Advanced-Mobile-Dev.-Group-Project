/*LogInFragment:
*(1) Displays UI elements for sign-in information entry
*(2) Sends entered data to parent activity*/

package edu.temple.mobiledevgroupproject.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import edu.temple.mobiledevgroupproject.R;

public class LogInFragment extends Fragment {
    //layout objects
    EditText userNameField;
    EditText passwordField;
    Button confirmButton;
    CheckBox rememberMeBox;

    LogInInterface logInListener;

    public LogInFragment() {
        // Required empty public constructor
    }

    public interface LogInInterface {
        void enteredUserName(String enteredUserName);
        void enteredPassword(String enteredPassWord);
        void saveInformation(boolean saveInfo);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log_in, container, false);
    }
}
