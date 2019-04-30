package edu.temple.mobiledevgroupproject.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class SimpleTime implements Serializable {
    public static String ANTE_MERIDIEM = "AM";
    public static String POST_MERIDIEM = "PM";

    private int hours;
    private int minutes;
    private String timePeriod;

    public SimpleTime(int hours, int minutes, String timePeriod) {
        this.hours = hours;
        this.minutes = minutes;
        this.timePeriod = timePeriod;
    }

    public SimpleTime(String timeStr){
        this.hours = Integer.valueOf(timeStr.substring(0,2));
        this.minutes = Integer.valueOf(timeStr.substring(3,5));
        if(timeStr.contains("a") || timeStr.contains("A")){
            this.timePeriod = ANTE_MERIDIEM;
        }
        else if(timeStr.contains("p") || timeStr.contains("P")){
            this.timePeriod = POST_MERIDIEM;
        }
        else{
            this.timePeriod = "";
        }
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    /**
     * Helper method.
     * Checks if startTime and endTime constitute a valid time interval within a given calendar day.
     */
    public static boolean isValidTimeInterval(SimpleTime startTime, SimpleTime endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        else if (startTime.getTimePeriod().equals(SimpleTime.POST_MERIDIEM) && endTime.getTimePeriod().equals(SimpleTime.ANTE_MERIDIEM)) {
            return false;
        } else if (startTime.getTimePeriod().equals(endTime.getTimePeriod())) {
            if (startTime.getHours() > endTime.getHours()) {
                return false;
            } else if (startTime.getHours() == endTime.getHours()) {
                if (startTime.getMinutes() <= endTime.getMinutes()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Helper Method.
     * @param time The time to be formatted.
     * @return a formatted string representation of time. param.
     */
    public static String formatTime(SimpleTime time) {
        if (time != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(time.getHours());
            sb.append(":");
            if (time.getMinutes() < 10) {
                sb.append("0");
            }
            sb.append(time.getMinutes());
            sb.append(" ");
            sb.append(time.getTimePeriod());
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * Constructs a JSONObject based on a SimpleDate instance's fields.
     * FORMAT: {"hours":<hours>,"minutes","time_period":<time period>}
     * @return a SimpleTime instance's fields in JSONObject format.
     */
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("hours", hours);
            jsonObject.put("minutes", minutes);
            jsonObject.put("time_period", timePeriod);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        return "" + getHours() + ":" + getMinutes();
    }
}
