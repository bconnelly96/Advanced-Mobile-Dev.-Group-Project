/*Job:
*(1) Represents a job to be posted by User*/

package edu.temple.mobiledevgroupproject.Objects;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Job implements Serializable {
    private String jobTitle;
    private String jobDescription;
    private SimpleDate datePosted;
    private SimpleDate dateOfJob;
    private LatLng location;
    private User user;
    private Record<Comment> commentList;

    public Job setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public Job setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public Job setDatePosted(SimpleDate datePosted) {
        this.datePosted = datePosted;
        return this;
    }

    public Job setDateOfJob(SimpleDate dateOfJob) {
        this.dateOfJob = dateOfJob;
        return this;
    }

    public Job setLocation(LatLng location) {
        this.location = location;
        return this;
    }

    public Job setUser(User user) {
        this.user = user;
        return this;
    }

    public Job setCommentList(Record<Comment> commentList) {
        this.commentList = commentList;
        return this;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public SimpleDate getDatePosted() {
        return datePosted;
    }

    public SimpleDate getDateOfJob() {
        return dateOfJob;
    }

    public LatLng getLocation() {
        return location;
    }

    public User getUser() {
        return user;
    }

    public Record<Comment> getCommentList() {
        return commentList;
    }

    //Returns a JSONObject containing values of instance's fields
    //{"title":<job title>,"desc":<job description>,"date":[<month>,<day>,<year>],"date_posted":[<month>,<day>,<year>],"loc_data":[<latitude>,<longitude>],"user_id":<job's user's ID>, "record_id""<job's record's ID>}
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", jobTitle);
            jsonObject.put("desc", jobDescription);

            JSONArray dateArray = new JSONArray();
            dateArray.put(dateOfJob.getMonth());
            dateArray.put(dateOfJob.getDay());
            dateArray.put(dateOfJob.getYear());
            jsonObject.put("date", dateArray);

            JSONArray dateArray2 = new JSONArray();
            dateArray2.put(datePosted.getMonth());
            dateArray2.put(datePosted.getDay());
            dateArray2.put(datePosted.getYear());
            jsonObject.put("date_posted", dateArray2);

            JSONArray locArray = new JSONArray();
            locArray.put(location.latitude);
            locArray.put(location.longitude);
            jsonObject.put("loc_data", locArray);

            jsonObject.put("user_id", user);
            jsonObject.put("record_id", commentList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}