/*User:
*(1) Represents a user of the application
*(2) Implemented using Builder pattern*/

package edu.temple.mobiledevgroupproject.Objects;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
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

public class User implements Parcelable {
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
    //bitmap image encoded to Base 64 String.
    private String profileImage;

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

    /*public User setprofileImage(Drawable profileImage) {
        this.profileImage = encodeToString(profileImage);
        return this;
    }*/

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

    public String getProfileImage() {
        return profileImage;
    }

    //return the Drawable form of user's profile image
   /* public Drawable getDecodedProfileImg() {
        return decodeToDrawable(profileImage);
    }*/

    public void updateCurrentEnrolledJobs(Job newJob) {
        currentEnrolledJobs.addDataToRecord(newJob);
    }

    public void updateCurrentPostedJobs(Job newJob) {
        currentPostedJobs.addDataToRecord(newJob);
    }

    public void updatePreviousJobs(Job newJob) {
        previousJobs.addDataToRecord(newJob);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeValue(userBirthDay);
        dest.writeValue(previousJobs);
        dest.writeValue(currentEnrolledJobs);
        dest.writeValue(currentPostedJobs);
        dest.writeDouble(userRating);
        dest.writeString(profileImage);
    }

    protected User(Parcel in) {
        name = in.readString();
        userName = in.readString();
        password = in.readString();
        userRating = in.readDouble();
        profileImage = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    //encode user's profile image to a Base 64 String
    /*private String encodeToString(Drawable profileImage) {

    }

    //decode a user's profile image to Drawable
    private Drawable decodeToDrawable(String profileImageString) {

    }*/


    /**
     * Constructs a JSONObject based on a User instance's fields.
     * FORMAT: {"full_name":<full name>,"name":<username>,"password":<password>,"age":<age>,"rating":<rating>,"posted_jobs":<record id>,"curr_jobs":<record id>,"prev_jobs":<record id>}
     * @return a User instance's fields in JSONObject format.
     */
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
}