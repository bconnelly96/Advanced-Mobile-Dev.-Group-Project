/*User:
*(1) Represents a user of the application
*(2) Implemented using Builder pattern*/

package edu.temple.mobiledevgroupproject.Objects;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class User implements Serializable {
    public static final double DEFAULT_RATING = 3.0;
    public static final double MIN_RATING = 0.0;
    public static final double MAX_RATING = 5.0;

    private String name;
    private String userName;
    private String password;
    private SimpleDate userBirthDay;
    private Record<Job> previousJobs;
    private Record<Job> currentEnrolledJobs;
    private Record<Job> currentPostedJobs;
    private double userRating;

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setUserBirthDay(SimpleDate userBirthDay) {
        this.userBirthDay = userBirthDay;
        return this;
    }

    public User setPreviousJobs(Record<Job> previousJobs) {
        this.previousJobs = previousJobs;
        return this;
    }

    public User setCurrentEnrolledJobs(Record<Job> currentEnrolledJobs) {
        this.currentEnrolledJobs = currentEnrolledJobs;
        return this;
    }

    public User setCurrentPostedJobs(Record<Job> currentPostedJobs) {
        this.currentPostedJobs = currentPostedJobs;
        return this;
    }

    public User setUserRating(double userRating) {
        this.userRating = userRating;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public SimpleDate getUserBirthDay() {
        return userBirthDay;
    }

    public Record<Job> getPreviousJobs() {
        return previousJobs;
    }

    public Record<Job> getCurrentEnrolledJobs() {
        return currentEnrolledJobs;
    }

    public Record<Job> getCurrentPostedJobs() {
        return currentPostedJobs;
    }

    public double getUserRating() {
        return userRating;
    }

    //Returns a JSONObject containing values of instance's fields
    //{"full_name":<full name>,"name":<username>,"password":<password>,"age":<age>,"rating":<rating>,"posted_jobs":<record id>,"curr_jobs":<record id>,"prev_jobs":<record id>}
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
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

    public static String hashAndSaltPassword(String password) {
        String hashedPassword = null;
        byte[] salt = new byte[16];
        new Random().nextBytes(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte [] hash = factory.generateSecret(spec).getEncoded();
            hashedPassword = Base64.encodeToString(hash, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
}