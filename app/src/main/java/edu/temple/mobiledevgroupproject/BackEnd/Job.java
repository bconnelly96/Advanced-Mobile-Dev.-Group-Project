package edu.temple.mobiledevgroupproject.BackEnd;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Date;

//TODO: Implement class using Builder Pattern
public class Job {
    private String jobTitle;
    private String jobDescription;
    private ArrayList<Drawable> jobImages;
    private Date datePosted;
    private Date dateOfJob;
    private String safetyConcerns;
    private String necessaryTools;
    private MapObject mapData;
    private User user;
}
