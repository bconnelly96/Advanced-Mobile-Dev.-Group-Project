/*Job:
*(1) Represents a job to be posted by User */

package edu.temple.mobiledevgroupproject.BackEnd;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;

//TODO: Implement class using Builder Pattern
public class Job {
    private String jobTitle;
    private String jobDescription;
    private ArrayList<Drawable> jobImages;
    private Date datePosted;
    private Date dateOfJob;
    private LatLng location;
    private User user;
    private ArrayList<Comment> comments;
}
