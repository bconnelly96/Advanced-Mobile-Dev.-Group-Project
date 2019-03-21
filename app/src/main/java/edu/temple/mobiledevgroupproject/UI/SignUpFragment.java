/*SignUpFragment:
*(1) Displays SignUp form to user
*(2) Gets sign up data from UI elements
*(3) Passes sign up data to parent activity*/

package edu.temple.mobiledevgroupproject.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class SignUpFragment extends Fragment {
    //layout objects
    EditText nameField;
    EditText userNameField;
    EditText passwordField;
    //TODO: add Date Picking component for user's birthday
    //TODO: add profile Image Picking component
    //TODO: add general Location picking component
    Button confirmButton;

    SignUpInterface signUpDataListener;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public interface SignUpInterface {
        void getSignUpData(User userData);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

}
