package edu.temple.mobiledevgroupproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import edu.temple.mobiledevgroupproject.Objects.Comment;
import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.Objects.Record;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.SimpleTime;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.UI.FormFragment;
import edu.temple.mobiledevgroupproject.UI.JobListFragment;
import edu.temple.mobiledevgroupproject.UI.JobViewActivity;
import edu.temple.mobiledevgroupproject.UI.MapFragment;
import edu.temple.mobiledevgroupproject.UI.ProfileFragment;

public class MainActivity extends AppCompatActivity implements JobListFragment.JobSelectedInterface, MapFragment.MapClickInterface, FormFragment.FormInterface {
    //layout objects
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    //fragment objects
    private FragmentManager fragmentManager;
    private ProfileFragment profileFragment;
    private FormFragment formFragment;
    private JobListFragment jobListFragment;
    private MapFragment mapFragment;
    //Data objects
    User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent recIntent = getIntent();
        if (recIntent != null) {
            //thisUser = recIntent.getParcelableExtra("this_user");
        }

        //testing***********************
        thisUser = new User();
        thisUser.setName("Brendan Connelly")
                .setUserName("bconnelly96")
                .setPassword("password123")
                .setUserBirthDay(new SimpleDate(10, 2, 1996))
                .setPreviousJobs(new Record<Job>("bconnelly_prev_jobs", Record.JOB_RECORD))
                .setCurrentEnrolledJobs(new Record<Job>("bconnelly_current_enrolled", Record.JOB_RECORD))
                .setCurrentPostedJobs(new Record<Job>("bconnelly_currently_posted", Record.JOB_RECORD))
                .setUserRating(User.DEFAULT_RATING);

        Job job1 = new Job(), job2 = new Job();
        job1.setJobTitle("Job 1: A great new job")
                .setJobDescription("This is such a good job.")
                .setDatePosted(new SimpleDate(10, 4, 2018))
                .setDateOfJob(new SimpleDate (10, 6, 2018))
                .setStartTime(new SimpleTime(10, 0, SimpleTime.ANTE_MERIDIEM))
                .setEndTime(new SimpleTime(2, 30, SimpleTime.POST_MERIDIEM))
                .setLocation(new LatLng(39.973862,  -75.158852))
                .setUser(thisUser)
                .setCommentList(new Record<Comment>("bconnelly_comment_list", Record.COMMENT_RECORD));

        job2.setJobTitle("Job 2: Anoter great new job")
                .setJobDescription("This is such a good job. Better than the first.")
                .setDatePosted(new SimpleDate(10, 4, 2018))
                .setDateOfJob(new SimpleDate(10, 6, 2018))
                .setStartTime(new SimpleTime(10, 0, SimpleTime.ANTE_MERIDIEM))
                .setEndTime(new SimpleTime(2, 30, SimpleTime.POST_MERIDIEM))
                .setLocation(new LatLng(39.953194, -75.163345))
                .setUser(thisUser)
                .setCommentList(new Record<Comment>("bconnelly_comment_list", Record.COMMENT_RECORD));

        final ArrayList<Job> jobsList = new ArrayList<>();
        jobsList.add(job1);
        jobsList.add(job2);

        final Bundle jobsBundle = new Bundle();
        jobsBundle.putSerializable("jobs_to_display", jobsList);

        //initialize layout objects
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.app_bar);

        //set up toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        fragmentManager = getSupportFragmentManager();
        mapFragment = new MapFragment();
        mapFragment.setArguments(jobsBundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();

        //listen for nav. item selected events
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                //launch different fragments depending on selected nav. menu item
                switch(menuItem.getItemId()) {
                    case R.id.nav_profile:
                        profileFragment = new ProfileFragment();
                        Bundle args2 = new Bundle();
                        args2.putParcelable("user_to_display", thisUser);
                        profileFragment.setArguments(args2);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                        break;
                    case R.id.nav_postjob:
                        formFragment = new FormFragment();
                        Bundle args3 = new Bundle();
                        args3.putParcelable("this_user", thisUser);
                        formFragment.setArguments(args3);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, formFragment).commit();
                        break;
                    case R.id.nav_joblist:
                        Bundle args = new Bundle();
                        args.putSerializable("job_list", jobsList);
                        jobListFragment = new JobListFragment();
                        jobListFragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, jobListFragment).commit();
                        break;
                    case R.id.nav_map:
                        mapFragment = new MapFragment();
                        mapFragment.setArguments(jobsBundle);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //handle user input on the action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            //refresh button
            case R.id.action_refresh:
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //implemented from JobListFragment.JobSelectedInterface
    @Override
    public void getSelectedJob(Job selectedJob) {
        launchJobViewActivity(selectedJob);
    }

    //implemented from MapFragment.MapClickInterface
    @Override
    public void jobSelected(Job selectedJob) {
        launchJobViewActivity(selectedJob);
    }

    //implemented from FormFragment.FormInterface
    @Override
    public void getDataFromForm(Job jobData, User user) {
        System.out.println("*******");
        System.out.println(jobData.toJSONObject().toString());
        System.out.println("*******");
        //TODO refine implementation
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
    }

    //helper method
    private void launchJobViewActivity(Job selectedJob) {
        Intent jobViewIntent = new Intent(this, JobViewActivity.class);
        jobViewIntent.putExtra("this_job", selectedJob);
        jobViewIntent.putExtra("this_user", thisUser);
        startActivity(jobViewIntent);
    }
}
