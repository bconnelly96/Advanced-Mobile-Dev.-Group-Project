package edu.temple.mobiledevgroupproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.temple.mobiledevgroupproject.BackEnd.Comment;
import edu.temple.mobiledevgroupproject.BackEnd.Job;
import edu.temple.mobiledevgroupproject.BackEnd.Record;
import edu.temple.mobiledevgroupproject.BackEnd.SimpleDate;
import edu.temple.mobiledevgroupproject.BackEnd.User;

public class MainActivity extends AppCompatActivity {

    @Override
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
        SimpleDate sd = new SimpleDate(1996, 10, 2);
        User.Builder.setUserBirthDay(sd);
        User.Builder.setPreviousJobs(testRecord);
        User.Builder.setCurrentEnrolledJobs(testRecord);
        User.Builder.setCurrentPostedJobs(testRecord);
        User.Builder.setUserRating(5.0);
        User testUser = User.Builder.build();
        System.out.println("*********TEST USER DATA*********");
        System.out.println(testUser.toJSONObject().toString());
        System.out.println("*********TEST USER DATA*********");

        //TEST THREE: Create Comment Objects;

        Comment c1 = new Comment("First Comment", );




        //TEST THREE: Create a Job Object; print its contents to console
        Job.Builder.setTitle("New Test Job");
        Job.Builder.setDescription("A new and exciting Test Job");
        SimpleDate sd2 = new SimpleDate(2019, 3, 14);
        Job.Builder.setDatePosted(sd2);
        SimpleDate sd3 = new SimpleDate(2019, 3, 20);
        Job.Builder.setDateOfJob(sd3);
        LatLng loc2 = new LatLng(30, 78);
        Job.Builder.setLocation(loc2);
        Job.Builder.setUser(testUser);
    }
}