/*Comment:
*(1) Represents a comment to be displayed on a specific job
*(2) Represents an object to be stored externally*/

package edu.temple.mobiledevgroupproject.BackEnd;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class Comment {
    private String text;
    private User user;
    private Calendar datePosted;
    private Job associatedJob;

    public Comment(String text, User user, Calendar datePosted, Job associatedJob) {
        this.text = text;
        this.user = user;
        this.datePosted = datePosted;
        this.associatedJob = associatedJob;
    }

    public String getCommentText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public Calendar getDatePosted() {
        return datePosted;
    }

    public Job getAssociatedJob() {
        return associatedJob;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDatePosted(Calendar datePosted) {
        this.datePosted = datePosted;
    }

    public void setAssociatedJob(Job associatedJob) {
        this.associatedJob = associatedJob;
    }

    //Returns a JSONObject containing values of instance's fields
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", text);
            jsonObject.put("date_posted", datePosted);
            jsonObject.put("user_id", user);
            jsonObject.put("job_id", associatedJob);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
