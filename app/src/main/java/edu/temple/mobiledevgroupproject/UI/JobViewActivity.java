package edu.temple.mobiledevgroupproject.UI;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class JobViewActivity extends AppCompatActivity {
    //layout objects
    TextView jobTitleView;
    TextView jobDescView;
    TextView jobDateView;
    TextView jobPostedView;
    TextView jobLocView;
    TextView jobUserView;
    Button confirmButton;
    //TODO: add UI elements to support commenting.

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

       /* jobTitleView = findViewById(R.id.job_title_view);
        jobDescView = findViewById(R.id.job_desc_view);
        jobDateView = findViewById(R.id.job_date_view);
        jobPostedView = findViewById(R.id.job_posted_view);
        jobLocView = findViewById(R.id.job_loc_view);
        jobUserView = findViewById(R.id.job_user_view);
        confirmButton = findViewById(R.id.confirm_button_jv);*/

        if (jobToDisplay != null) {
            jobTitleView.setText(jobToDisplay.getJobTitle());
            jobDescView.setText(jobToDisplay.getJobDescription());
            jobDateView.setText(getDateString(jobToDisplay.getDateOfJob()));
            jobPostedView.setText(getDateString(jobToDisplay.getDatePosted()));
            jobLocView.setText(getAddrFromLatLng(jobToDisplay.getLocation()));
            jobUserView.setText(jobToDisplay.getUser().getUserName());
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDialog();
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
}
