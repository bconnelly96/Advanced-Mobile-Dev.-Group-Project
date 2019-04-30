/*LogInActivity
 *(1) Serves as the entry point of the application
 *(2) Listens for user selection to login/signup
 *(3) Signs user in automatically if sign-in info has been saved locally
 *(4) Creates and launches fragments according to login/signup selection
 *(5) Launches MainActivity.java via Intent upon successful login/signup*/

package edu.temple.mobiledevgroupproject.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import edu.temple.mobiledevgroupproject.MainActivity;
import edu.temple.mobiledevgroupproject.Objects.SharedPrefManager;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class LogInActivity extends AppCompatActivity implements LogInFragment.LogInInterface, SignUpFragment.SignUpInterface {
    private static final String FILENAME = "saved_credentials";

    //layout objects
    FrameLayout fragmentContainer;
    TextView newTextView;

    User thisUser;
    String retrievedUserName = null;
    Intent launchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        fragmentContainer = findViewById(R.id.login_frag_container);
        LogInFragment logInFragment = new LogInFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.login_frag_container, logInFragment).commit();
    }

    /**
     * Write username, password pair to save file with JSON format [<username> , <password>]
     * Send existing user User object to database.
     * Launch MainActivity.
     * @param existingUser validated User object received from LogInFragment.java
     * @param saveData boolean data specifying whether user wishes to create a save file for automatic login
     */
    @Override
    public void sendExistingUser(User existingUser, boolean saveData) {
        startMainActivity(null);
    }

    /**
     * Triggered in child fragment, when user requests to create new account.
     * Launches new fragment
     */
    @Override
    public void signUpClick() {
        SignUpFragment signUpFragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.login_frag_container, signUpFragment).commit();
        //Toast.makeText(LogInActivity.this, "clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * Sends new user information to database.
     * Launches MainActivity.
     * @param newUser User object constructed and received from SignUpFragment.java
     */
    @Override
    public void sendNewUser(User newUser) {
        sendUserToDataBase(newUser);
        startMainActivity(newUser);
    }

    /**
     * Send signup/login user information to database
     * @param user User object to be sent to database
     */
    private void sendUserToDataBase(User user) {

    }

    /**
     * Create a new Intent; use it to launch MainActivity.class
     * @param extraData User object to be passed to MainActivity.class as Intent extra
     */
    private void startMainActivity(User extraData) {
        launchIntent = new Intent(this, MainActivity.class);
        launchIntent.putExtra("this_user", extraData);
        finish();
        startActivity(launchIntent);
    }

}
