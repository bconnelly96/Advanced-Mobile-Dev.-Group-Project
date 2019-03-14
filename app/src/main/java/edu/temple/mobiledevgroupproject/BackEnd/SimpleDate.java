package edu.temple.mobiledevgroupproject.BackEnd;

import org.json.JSONException;
import org.json.JSONObject;

public class SimpleDate {
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

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    //Returns a JSONObject containing values of instance's fields
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("year", year);
            jsonObject.put("month", month);
            jsonObject.put("day", day);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
