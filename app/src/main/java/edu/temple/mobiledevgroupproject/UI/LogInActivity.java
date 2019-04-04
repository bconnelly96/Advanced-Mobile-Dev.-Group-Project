/*LogInActivity
 *(1) Serves as the entry point of the application
 *(2) Listens for user selection to login/signup
 *(3) Signs user in automatically if sign-in info has been saved locally
 *(4) Creates and launches fragments according to login/signup selection
 *(5) Launches MainActivity.java via Intent upon successful login/signup*/

package edu.temple.mobiledevgroupproject.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import edu.temple.mobiledevgroupproject.MainActivity;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class LogInActivity extends AppCompatActivity implements LogInFragment.LogInInterface, SignUpFragment.SignUpInterface {
    private static final String FILENAME = "saved_credentials";

    //layout objects
    Button logInButton;
    Button signUpButton;
    FrameLayout fragmentContainer;

    User thisUser;
    String retrievedUserName = null;
    Intent launchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        logInButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.signup_button);
        //fragmentContainer = findViewById(R.id.login_frag_container);

        if (savedDataExists()) {
            startMainActivity(thisUser);
        } else {
            logInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogInFragment logInFragment = new LogInFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.login_frag_container, logInFragment);
                }
            });

            signUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logInButton.setVisibility(View.INVISIBLE);
                    signUpButton.setVisibility(View.INVISIBLE);
                    fragmentContainer.setVisibility(View.VISIBLE);

                    SignUpFragment signUpFragment = new SignUpFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.login_frag_container, signUpFragment);
                }
            });
        }
    }

    //receive existing user from signin fragment; send to database; pass as extra to Intent; write to file
    @Override
    public void sendExistingUser(User existingUser, boolean saveData) {
        //write to file
        if (saveData) {
            File file = new File(getFilesDir(), FILENAME);
            JSONArray jsonArray = null;
            jsonArray.put(existingUser.getUserName());
            jsonArray.put(existingUser.getPassword());
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(jsonArray.toString().getBytes());
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //TODO SEND TO DATABASE

        startMainActivity(existingUser);
    }

    //receive new user from signup fragment; send to database, pass as an extra to Intent, launch main activity
    @Override
    public void sendNewUser(User newUser) {
        //TODO SEND TO DATABASE

        startMainActivity(newUser);
    }

    //look for user login data in internal storage
    //set username field if so
    //return true if user login data exists
    private boolean savedDataExists() {
        File file = new File(getFilesDir(), FILENAME);
        boolean fileExists = false;

        if (file.exists()) {
            fileExists = true;
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String currLine;
                while ((currLine = br.readLine()) != null) {
                    sb.append(currLine);
                    sb.append('\n');
                }
                br.close();
                JSONArray jsonArray = new JSONArray(sb.toString());
                /*username/password written into json array with format:
                 *["<username>" , "<password>"]*/
                retrievedUserName = jsonArray.getString(0);

                //TODO GET USER DATA FROM DATABASE, CREATE NEW USER OBJECT.

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return fileExists;
    }

    public void sendUserToDataBase(User user) {

    }

    public void startMainActivity(User extraData) {
        launchIntent = new Intent(this, MainActivity.class);
        launchIntent.putExtra("user", extraData);
        startActivity(launchIntent);
    }

}
