package edu.temple.mobiledevgroupproject.BackEnd;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Date;

//TODO: Implement class using Builder Pattern
public class User {
    private String name;
    private String userName;
    private String password;
    private Date userBirthDay;
    private MapObject generalLoc;
    private ArrayList<Job> previousJobs;
    private ArrayList<Job> currentJobs;
    private double userRating;
    private Drawable profileImage;
}
