package edu.temple.mobiledevgroupproject.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.temple.mobiledevgroupproject.BackEnd.User;
import edu.temple.mobiledevgroupproject.R;

public class LogInActivity extends AppCompatActivity implements LogInFragment.LogInInterface, SignUpFragment.SignUpInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }




    //implemented methods
    @Override
    public void enteredUserName(String enteredUserName) {

    }

    @Override
    public void enteredPassword(String enteredPassWord) {

    }

    @Override
    public void saveInformation(boolean saveInfo) {

    }

    @Override
    public void getSignUpData(User userData) {

    }
}
