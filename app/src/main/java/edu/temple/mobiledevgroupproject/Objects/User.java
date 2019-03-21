/*User:
*(1) Represents a user of the application
*(2) Implemented using Builder pattern*/

package edu.temple.mobiledevgroupproject.Objects;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    public static final String CIVILIAN = "civilian";
    public static final String ORGANIZATION = "organization";

    private String name;
    private String userClass;
    private String userName;
    private String password;
    private SimpleDate userBirthDay;
    private Record<Job> previousJobs;
    private Record<Job> currentEnrolledJobs;
    private Record<Job> currentPostedJobs;
    private double userRating;

    private User(String name, String userClass, String userName, String password, SimpleDate userBirthDay, Record<Job> previousJobs, Record<Job> currentEnrolledJobs, Record<Job> currentPostedJobs, double userRating) {
        this.name = name;
        this.userClass = userClass;
        this.userName = userName;
        this.password = password;
        this.userBirthDay = userBirthDay;
        this.previousJobs = previousJobs;
        this.currentEnrolledJobs = currentEnrolledJobs;
        this.currentPostedJobs = currentPostedJobs;
        this.userRating = userRating;
    }

    public static class Builder {
        private static String name;
        private static String userClass;
        private static String userName;
        private static String password;
        private static SimpleDate userBirthDay;
        private static Record<Job> previousJobs;
        private static Record<Job> currentEnrolledJobs;
        private static Record<Job> currentPostedJobs;
        private static double userRating;

        public static void setName(String name) {
            User.Builder.name = name;
        }

        public static void setUserClass(String userClass) {
            User.Builder.userClass = userClass;
        }

        public static void setUserName(String userName) {
            User.Builder.userName = userName;
        }

        public static void setPassword(String password) {
            User.Builder.password = password;
        }

        public static void setUserBirthDay(SimpleDate userBirthDay) {
            User.Builder.userBirthDay = userBirthDay;
        }

        public static void setPreviousJobs(Record<Job> previousJobs) {
            User.Builder.previousJobs = previousJobs;
        }

        public static void setCurrentEnrolledJobs(Record<Job> currentEnrolledJobs) {
            User.Builder.currentEnrolledJobs = currentEnrolledJobs;
        }

        public static void setCurrentPostedJobs(Record<Job> currentPostedJobs) {
            User.Builder.currentPostedJobs = currentPostedJobs;
        }

        public static void setUserRating(double userRating) {
            User.Builder.userRating = userRating;
        }

        public static User build() {
            return new User(name, userClass, userName, password, userBirthDay, previousJobs, currentEnrolledJobs, currentPostedJobs, userRating);
        }
    }

    //Returns a JSONObject containing values of instance's fields
    //{"class":<class>,"full_name":<full name>,"name":<username>,"password":<password>,"age":<age>,"rating":<rating>,"posted_jobs":<record id>,"curr_jobs":<record id>,"prev_jobs":<record id>}
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("class", userClass);
            jsonObject.put("full_name", name);
            jsonObject.put("name", userName);
            jsonObject.put("password", password);
            jsonObject.put("age", userBirthDay);
            jsonObject.put("rating", userRating);
            jsonObject.put("posted_jobs", currentPostedJobs);
            jsonObject.put("curr_jobs", currentEnrolledJobs);
            jsonObject.put("prev_jobs", previousJobs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}