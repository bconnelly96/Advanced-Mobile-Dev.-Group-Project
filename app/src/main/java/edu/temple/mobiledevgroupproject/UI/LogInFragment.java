/*LogInFragment:
*(1) Displays UI elements for sign-in information entry
*(2) Performs verification on entered Data by Querying Databse
*(3) Constructs object with data returned from Database
*(4) Passes object up to parent Activity*/

package edu.temple.mobiledevgroupproject.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.temple.mobiledevgroupproject.Objects.Constants;
import edu.temple.mobiledevgroupproject.Objects.RequestHandler;
import edu.temple.mobiledevgroupproject.Objects.SharedPrefManager;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class LogInFragment extends Fragment {
    //layout objects
    EditText userNameField;
    EditText passwordField;
    Button confirmButton;
    CheckBox rememberMeBox;
    TextView newTextView;
    ProgressDialog progressDialog;

    LogInInterface logInListener;
    View view;

    public LogInFragment() {
        // Required empty public constructor
    }

    public interface LogInInterface {
        void sendExistingUser(User user, boolean saveData);
        void signUpClick();
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
        newTextView = view.findViewById(R.id.new_text_view);
        progressDialog = new ProgressDialog(getContext());

        newTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInListener.signUpClick();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameField.getText().toString();
                String password = passwordField.getText().toString();
                if (allFieldsHaveInput()) {

                    //DB
                    userLogin(userName, password);
                    //DB
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

    /**
     * Check the value of each UI input to ensure all login fields have some input
     * @return true if all fields have some input
     */
    private boolean allFieldsHaveInput() {
        return (userNameField.getText().toString().trim().length() != 0) &&
                (passwordField.getText().toString().trim().length() != 0);
    }

    //Create a new user object based on the data in the database
    private void userLogin(final String userName, final String password){
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.hide();
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                SharedPrefManager.getInstance(getContext()).userLogin(jsonObject.getInt("id"),
                                        jsonObject.getString("name"), jsonObject.getString("userName"),
                                        jsonObject.getString("birthday"), jsonObject.getString("previousJobs"),
                                        jsonObject.getDouble("rating"));
                                logInListener.sendExistingUser(null, true);
                            }
                            else{
                                Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userName",userName);
                params.put("password",password);
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


}


