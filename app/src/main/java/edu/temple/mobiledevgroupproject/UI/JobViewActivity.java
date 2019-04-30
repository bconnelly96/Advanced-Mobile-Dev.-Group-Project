package edu.temple.mobiledevgroupproject.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.temple.mobiledevgroupproject.Objects.Comment;
import edu.temple.mobiledevgroupproject.Objects.Constants;
import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.Objects.Record;
import edu.temple.mobiledevgroupproject.Objects.RequestHandler;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.SimpleTime;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class JobViewActivity extends AppCompatActivity implements CommentFragment.CommentPostedInterface {
    ///layout objects
    TextView jobTitleView;
    TextView jobDescView;
    TextView jobDateView;
    TextView jobPostedView;
    TextView startTimeView;
    TextView endTimeView;
    TextView jobLocView;
    TextView viewComments;
    Button confirmButton;
    FrameLayout container;
    ProgressDialog progressDialog;

    Job jobToDisplay;
    User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);

        Intent recIntent = getIntent();
        if (recIntent != null) {
            jobToDisplay = (Job) recIntent.getParcelableExtra("this_job");
            thisUser = (User) recIntent.getParcelableExtra("this_user");
        }

        jobTitleView = findViewById(R.id.job_title_view);
        jobDescView = findViewById(R.id.job_desc_view);
        jobDateView = findViewById(R.id.job_date_view);
        jobPostedView = findViewById(R.id.job_posted_view);
        startTimeView = findViewById(R.id.job_start_view);
        endTimeView = findViewById(R.id.job_end_view);
        jobLocView = findViewById(R.id.job_loc_view);
        viewComments = findViewById(R.id.see_comment_view);
        confirmButton = findViewById(R.id.confirm_button_jv);
        progressDialog = new ProgressDialog(this);

        if (jobToDisplay != null) {
            jobTitleView.setText(jobToDisplay.getJobTitle());
            jobDescView.setText(jobToDisplay.getJobDescription());
            jobDateView.setText("Date of Opportunity: " + getDateString(jobToDisplay.getDateOfJob()));
            jobPostedView.setText("Date Posted: " + getDateString(jobToDisplay.getDatePosted()));
            String startHours = String.valueOf(jobToDisplay.getStartTime().getHours());
            String startMins = String.valueOf(jobToDisplay.getStartTime().getMinutes());
            String startPeriod = jobToDisplay.getStartTime().getTimePeriod();
            startTimeView.setText("Start Time: " + startHours + ":" + startMins + " " + startPeriod);
            String endHours = String.valueOf(jobToDisplay.getEndTime().getHours());
            String endMins = String.valueOf(jobToDisplay.getEndTime().getMinutes());
            String endPeriod = jobToDisplay.getEndTime().getTimePeriod();
            endTimeView.setText("End Time: " + endHours + ":" + endMins + " " + endPeriod);
            jobLocView.setText(getAddrFromLatLng(jobToDisplay.getLocation()));
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDialog();
            }
        });

        getCommentsFromDatabase();

        viewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentFragment commentFragment = new CommentFragment();
                Bundle args = new Bundle();
                args.putParcelable("this_job", jobToDisplay);
                args.putParcelable("this_user", thisUser);
                commentFragment.setArguments(args);
                container = findViewById(R.id.frag_container);
                container.setVisibility(View.VISIBLE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, commentFragment).addToBackStack(null).commit();
            }
        });
    }

    /**
     * Helper method.
     * Use Geocoder to translate a LatLng coordinate object into a traditional address.
     * @param latLng A LatLng object to be translated into an address String
     * @return An address String based on the latLng param.
     */
    private String getAddrFromLatLng(LatLng latLng) {
        String addressString = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address retAddress = addresses.get(0);
                StringBuilder sb = new StringBuilder("");
                for (int i = 0; i <= retAddress.getMaxAddressLineIndex(); i++) {
                    sb.append(retAddress.getAddressLine(i)).append("\n");
                }
                addressString = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return addressString;
    }

    /**
     * Helper method.
     * Assemble a String of the format: "month/day/year"
     * @param simpleDate A SimpleDate instance representing the date to be formatted.
     * @return A formatted date string for display purposes.
     */
    private String getDateString(SimpleDate simpleDate) {
        StringBuilder sb = new StringBuilder("");
        sb.append(String.valueOf(simpleDate.getMonth()));
        sb.append("/");
        sb.append(String.valueOf(simpleDate.getDay()));
        sb.append("/");
        sb.append(String.valueOf(simpleDate.getYear()));
        return sb.toString();
    }

    /**
     * Helper method.
     * Presents user with a dialog confirming their selection of a given job.
     * Upon a click of the dialog's 'confirm' button, user will be 'signed-up' for
     * this job, and the Activity will close.
     */
    private void launchDialog() {
        View mView = getLayoutInflater().inflate(R.layout.confirm_dialog, null);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        Button dialogConfirm = mView.findViewById(R.id.dialog_confirm);
        Button dialogCancel = mView.findViewById(R.id.dialog_cancel);

        dialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisUser.updateCurrentEnrolledJobs(jobToDisplay);
                thisUser.updatePreviousJobs(jobToDisplay);
                dialog.dismiss();
                Toast.makeText(JobViewActivity.this, getResources().getString(R.string.job_added), Toast.LENGTH_LONG).show();
                jobToDisplay = null;
                thisUser = null;
                finish();
            }
        });

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Implemented from CommentFragment.CommentPostedInterface
     * Add comment to this Job instance's commentList Record field.
     * @param comment The newly posted comment to be added.
     */
    @Override
    public void getPostedComment(Comment comment) {
        //TODO implement
        jobToDisplay.updateCommentList(comment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (container != null) {
            container.setVisibility(View.GONE);
        }
    }

    private void getCommentsFromDatabase(){
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.GET_COMMENTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CommentResponse", response);
                        response = response.substring(1, response.length()-1);
                        String[] commentArr = response.split(", ");
                        Comment temp;
                        for(int i = 0; i < commentArr.length; i++){
                            Log.d("Comment", commentArr[i]);
                            temp = new Comment(commentArr[i], thisUser, new SimpleDate("2019-04-29"));
                            jobToDisplay.updateCommentList(temp);
                        }
                        progressDialog.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("jobTitle",jobToDisplay.getJobTitle());
                return params;
            }
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}