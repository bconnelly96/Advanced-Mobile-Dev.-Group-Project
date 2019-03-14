package edu.temple.mobiledevgroupproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.temple.mobiledevgroupproject.BackEnd.Comment;
import edu.temple.mobiledevgroupproject.BackEnd.Job;
import edu.temple.mobiledevgroupproject.BackEnd.Record;
import edu.temple.mobiledevgroupproject.BackEnd.User;

public class MainActivity extends AppCompatActivity {

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEST ONE: Create a Record Object; print its contents to console
        ArrayList<String> recordData = new ArrayList<>();
        recordData.add("data1");
        recordData.add("data2");
        recordData.add("data3");
        Record testRecord = new Record(recordData, "TEST RECORD", "STRING RECORD");
        System.out.println("*********TEST RECORD DATA*********");
        System.out.println(testRecord.toJSONObject().toString());
        System.out.println("*********TEST RECORD DATA*********");

        //TEST TWO: Create a User Object; print its contents to console
        User.Builder.setName("Test Name");
        User.Builder.setUserClass("Civilian");
        User.Builder.setUserName("civilianUser101");
        User.Builder.setPassword("password1234");

        Calendar cal = Calendar.getInstance();

        User.Builder.setUserBirthDay();
    }*/
}
