/*Job:
*(1) Represents a job to be posted by User
*(2) Implemented using Builder pattern */

package edu.temple.mobiledevgroupproject.BackEnd;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class Job {
    private String jobTitle;
    private String jobDescription;
    private SimpleDate datePosted;
    private SimpleDate dateOfJob;
    private LatLng location;
    private User user;
    private Record<Comment> comments;

    private Job(String jobTitle, String jobDescription, SimpleDate datePosted, SimpleDate dateOfJob, LatLng location, User user, Record<Comment> comments) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.datePosted = datePosted;
        this.dateOfJob = dateOfJob;
        this.location = location;
        this.user = user;
        this.comments = comments;
    }

    public static class Builder {
        private static String jobTitle;
        private static String jobDescription;
        private static SimpleDate datePosted;
        private static SimpleDate dateOfJob;
        private static LatLng location;
        private static User user;
        private static Record<Comment> comments;

        public static void setTitle(String jobTitle) {
            Job.Builder.jobTitle = jobTitle;
        }

        public static void setDescription(String jobDescription) {
            Job.Builder.jobDescription = jobDescription;
        }

        public static void setDatePosted(SimpleDate datePosted) {
            Job.Builder.datePosted = datePosted;
        }

        public static void setDateOfJob(SimpleDate dateOfJob) {
            Builder.dateOfJob = dateOfJob;
        }

        public static void setLocation(LatLng location) {
            Job.Builder.location = location;
        }

        public static void setUser(User user) {
            Job.Builder.user = user;
        }

        public static void setComments(Record<Comment> comments) {
            Job.Builder.comments = comments;
        }

        public static Job build() {
            return new Job(jobTitle, jobDescription, datePosted, dateOfJob, location, user, comments);
        }
    }

    //Returns a JSONObject containing values of instance's fields
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", jobTitle);
            jsonObject.put("desc", jobDescription);
            jsonObject.put("date", dateOfJob);
            jsonObject.put("date_poster", datePosted);
            jsonObject.put("loc_data", location);
            jsonObject.put("user_id", user);
            jsonObject.put("record_id", comments);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}