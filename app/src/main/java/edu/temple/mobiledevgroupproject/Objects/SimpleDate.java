package edu.temple.mobiledevgroupproject.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SimpleDate implements Serializable {
    private int year;
    private int month;
    private int day;

    public SimpleDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /**
     * Constructs a JSONObject based on a SimpleDate instance's fields.
     * FORMAT: {"month":<month>,"day":<day>,"year":<year>}
     * @return a SimpleDate instance's fields in JSONObject format.
     */
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("month", month);
            jsonObject.put("day", day);
            jsonObject.put("year", year);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
