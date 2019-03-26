/*LogInFragment:
*(1) Displays UI elements for sign-in information entry
*(2) Performs verification on entered Data by Querying Databse
*(3) Constructs object with data returned from Database
*(4) Passes object up to parent Activity*/

package edu.temple.mobiledevgroupproject.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class LogInFragment extends Fragment {
    //layout objects
    EditText userNameField;
    EditText passwordField;
    Button confirmButton;
    CheckBox rememberMeBox;

    LogInInterface logInListener;
    View view;

    public LogInFragment() {
        // Required empty public constructor
    }

    public interface LogInInterface {
        void sendExistingUser(User existingUser, boolean saveData);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        logInListener = (LogInInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logInListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_in, container, false);

        userNameField = view.findViewById(R.id.username_et_log);
        passwordField = view.findViewById(R.id.password_edit_text_log);
        confirmButton = view.findViewById(R.id.confirm_button_log);
        rememberMeBox = view.findViewById(R.id.checkbox_log);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = userNameField.getText().toString();
                String password = passwordField.getText().toString();
                if (allFieldsHaveInput() && logInInfoValid(userName, password)) {

                    User constructedExistingUser = constructExistingUser(userName, password);
                    if (rememberMeBox.isChecked()) {
                        logInListener.sendExistingUser(constructedExistingUser, true);
                    } else {
                        logInListener.sendExistingUser(constructedExistingUser, false);
                    }
                    Toast.makeText(getContext(), R.string.login_success, Toast.LENGTH_SHORT).show();
                } else {
                    if (allFieldsHaveInput()) {
                        Toast.makeText(getContext(), R.string.login_fail, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    private boolean allFieldsHaveInput() {
        return (userNameField.getText().toString().trim().length() != 0) &&
                (passwordField.getText().toString().trim().length() != 0);
    }

    /*Checks if there is a user with userName in database.
    * Checks that the password entered matches a hashed password corresponding with that user in database
    * Returns true if both userName and password are valid*/
    private boolean logInInfoValid(String userName, String password) {
        return false;
    }

    /*Query database for data of user with username 'userName'
    * Construct and return User object with returned data.*/
    private User constructExistingUser(String userName, String password) {
        return null;
    }
}


