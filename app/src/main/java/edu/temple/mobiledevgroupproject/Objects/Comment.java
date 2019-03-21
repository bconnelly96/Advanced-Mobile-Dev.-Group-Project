/*Comment:
*(1) Represents a comment to be displayed on a specific job
*(2) Represents an object to be stored externally*/

package edu.temple.mobiledevgroupproject.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    //Returns a JSONObject containing values of instance's fields
    //{"text":<comment's text>,"date_posted":[<month>,<day>,<year>],"user_id":<comment's user's ID>}
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("text", text);

            JSONArray dateArray = new JSONArray();
            dateArray.put(datePosted.getMonth());
            dateArray.put(datePosted.getDay());
            dateArray.put(datePosted.getYear());
            jsonObject.put("date_posted", dateArray);

            jsonObject.put("user_id", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
