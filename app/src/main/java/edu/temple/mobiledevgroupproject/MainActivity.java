package edu.temple.mobiledevgroupproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.temple.mobiledevgroupproject.Objects.Comment;
import edu.temple.mobiledevgroupproject.Objects.Record;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEST ONE: Create a Record Object; print its contents to console


        //TEST TWO: Create a User Object; print its contents to console
        User.Builder.setName("Test Name");
        User.Builder.setUserClass("Civilian");
        User.Builder.setUserName("civilianUser101");
        User.Builder.setPassword("password1234");
        SimpleDate sd = new SimpleDate(1996, 10, 2);
        User.Builder.setUserBirthDay(sd);
       // User.Builder.setPreviousJobs(testRecord);
      //  User.Builder.setCurrentEnrolledJobs(testRecord);
       // User.Builder.setCurrentPostedJobs(testRecord);
        User.Builder.setUserRating(5.0);
        User testUser = User.Builder.build();
        System.out.println("*********TEST USER DATA*********");
        System.out.println(testUser.toJSONObject().toString());
        System.out.println("*********TEST USER DATA*********");

        //TEST THREE: Create Comment Objects; add them to new record; print record contents to console
        SimpleDate sd1 = new SimpleDate(2019, 3, 12);
        SimpleDate sd2 = new SimpleDate(2019, 3, 13);
        SimpleDate sd3 = new SimpleDate(2019, 3, 14);

        Comment c1 = new Comment("First Comment", testUser, sd1);
        Comment c2 = new Comment("Second Comment", testUser, sd2);
        Comment c3 = new Comment("Third Comment", testUser, sd3);
        Record newTestRecord = new Record<Comment>("test comment record", Record.COMMENT_RECORD);
        newTestRecord.addDataToRecord(c1);
        newTestRecord.addDataToRecord(c2);
        newTestRecord.addDataToRecord(c3);
        System.out.println(newTestRecord.toJSONObject().toString());



        //TEST THREE: Create a Job Object; print its contents to console
        /*Job.Builder.setTitle("New Test Job");
        Job.Builder.setDescription("A new and exciting Test Job");
        SimpleDate sd4 = new SimpleDate(2019, 3, 14);
        Job.Builder.setDatePosted(sd4);
        SimpleDate sd5 = new SimpleDate(2019, 3, 20);
        Job.Builder.setDateOfJob(sd5);
        LatLng loc2 = new LatLng(30, 78);
        Job.Builder.setLocation(loc2);
        Job.Builder.setUser(testUser);*/
    }
}