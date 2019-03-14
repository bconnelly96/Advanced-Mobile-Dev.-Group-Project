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
    private SimpleDate datePosted;

    public Comment(String text, User user, SimpleDate datePosted) {
        this.text = text;
        this.user = user;
        this.datePosted = datePosted;;
    }

    public String getCommentText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public SimpleDate getDatePosted() {
        return datePosted;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDatePosted(SimpleDate datePosted) {
        this.datePosted = datePosted;
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
