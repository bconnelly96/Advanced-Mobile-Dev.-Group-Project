package edu.temple.mobiledevgroupproject.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.R;

public class JobViewActivity extends AppCompatActivity {
    //layout objects
    TextView jobTitleView;
    TextView jobDescView;
    TextView jobDateView;
    TextView jobPostedView;
    TextView jobLocView;
    TextView jobUserView;
    //TODO: add UI elements to support commenting.

    Job jobToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_view);

        Intent recIntent = getIntent();
        if (recIntent != null) {
            jobToDisplay = (Job) recIntent.getSerializableExtra("this_job");
        }
    }
}
