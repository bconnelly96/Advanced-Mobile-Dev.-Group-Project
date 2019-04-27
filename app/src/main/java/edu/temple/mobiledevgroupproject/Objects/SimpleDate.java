package edu.temple.mobiledevgroupproject.Objects;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Calendar;

public class SimpleDate implements Serializable {
    private int year;
    private int month;
    private int day;

    public SimpleDate(int month, int day, int year) {
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
     * Helper method.
     * Checks if a date with the attributes passed in as args. is a valid date.
     * Accounts for leap year and number of days in a month.
     * @return True if the params. match a valid date.
     */
    public static boolean isValidDate(int month, int day, int year) {
        //check simple invalidity first
        if ((month <= 0 || month > 12) || (day <= 0)) {
            return false;
        }

        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                return false;
            }
        } else if (month == 2) {
            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                if (day > 29) {
                    return false;
                }
            } else {
                if (day > 28) {
                    return false;
                }
            }
        } else {
            if (day > 31) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method.
     * Calculate a user's age based on the current date and the userBirthDay param.
     * @param userBirthDay User instance's field representing their D.O.B.
     * @return A string representing the user's age relative to the current date. Null if the birthday is not a valid date or if param. is null.
     */
    public static String getAgeString(SimpleDate userBirthDay) {
        Calendar cal = Calendar.getInstance();
        int currDay = cal.get(Calendar.DAY_OF_MONTH);
        int currMonth = cal.get(Calendar.MONTH);
        int currYear = cal.get(Calendar.YEAR);

        int yearDiff;
        if (userBirthDay != null && isValidDate(userBirthDay.getMonth(), userBirthDay.getDay(), userBirthDay.getYear())) {
            yearDiff = currYear - userBirthDay.getYear();
            int monthDiff = currMonth - userBirthDay.getMonth();

            if (monthDiff < 0) {
                yearDiff -= 1;
            } else if (monthDiff == 0) {
                int dayDiff = currDay - userBirthDay.getDay();
                if (dayDiff < 0) {
                    yearDiff -= 1;
                }
            }
            return String.valueOf(yearDiff);
        }
        return null;
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
