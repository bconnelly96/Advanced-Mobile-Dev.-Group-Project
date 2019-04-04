package edu.temple.mobiledevgroupproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.UI.FormFragment;
import edu.temple.mobiledevgroupproject.UI.JobListFragment;
import edu.temple.mobiledevgroupproject.UI.JobViewActivity;
import edu.temple.mobiledevgroupproject.UI.MapFragment;
import edu.temple.mobiledevgroupproject.UI.ProfileFragment;

public class MainActivity extends AppCompatActivity implements JobListFragment.JobSelectedInterface {
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
    ArrayList<User> userList;
    User thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent recIntent = getIntent();
        if (recIntent != null) {
            thisUser = (User) recIntent.getSerializableExtra("this_user");
        }

        //initialize layout objects
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.app_bar);

        //set up toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //TEST MAP
        Job job1 = new Job(), job2 = new Job(), job3 = new Job();
        job1.setJobTitle("Liacouras Job")
                .setLocation(new LatLng(39.973862, -75.158852))
                .setJobDescription("A very rewarding experience at the Liacouras center!")
                .setDateOfJob(new SimpleDate(2019, 11, 1));

        job2.setJobTitle("Paley Job")
                .setLocation(new LatLng(39.980942, -75.154465))
                .setJobDescription("A very rewarding experience at the Paley Library. This is going to be a very long description to test what happens when the job's description is very long.")
                .setDateOfJob(new SimpleDate(2019, 11, 3));

        job3.setJobTitle("City Hall Job")
                .setLocation(new LatLng(39.953194, -75.163345))
                .setJobDescription("City Hall. Den of scum and villainy")
                .setDateOfJob(new SimpleDate(1969, 4, 20));

        ArrayList<Job> testJobs = new ArrayList<>();
        testJobs.add(job1);
        testJobs.add(job2);
        testJobs.add(job3);

        final Bundle mapTestBundle = new Bundle();
        mapTestBundle.putSerializable("jobs_to_display", testJobs);
        //TEST MAP








        fragmentManager = getSupportFragmentManager();
        mapFragment = new MapFragment();

        mapFragment.setArguments(mapTestBundle);

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
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                        break;
                    case R.id.nav_postjob:
                        formFragment = new FormFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, formFragment).commit();
                        break;
                    case R.id.nav_joblist:
                        ArrayList<Job> testJobs = new ArrayList<>();
                        Job job1 = new Job(), job2 = new Job(), job3 = new Job(), job4 = new Job(), job5 = new Job(), job6 = new Job(), job7 = new Job();

                        job1.setJobTitle("Test 1")
                                .setJobDescription("DEScription test1 this is a long description. tes test tes hello hello hello hello world test LOREM DEScription test1 this is a long description. tes test tes hello hello hello hello world test LOREM")
                                .setUser(new User().setUserName("TEST USER 1"))
                                .setDateOfJob(new SimpleDate(2019, 10, 2));

                        job2.setJobTitle("Test 2")
                                .setJobDescription("short desc.")
                                .setUser(new User().setUserName("TEST USER 2"))
                                .setDateOfJob(new SimpleDate(2019, 11, 22));

                        job3.setJobTitle("Test 3")
                                .setJobDescription("medium desc.")
                                .setUser(new User().setUserName("TEST USER 3"))
                                .setDateOfJob(new SimpleDate(2016, 4, 1));

                        job4.setJobTitle("Test 4")
                                .setJobDescription("blow blow bloaw jah rastafari real hip hop")
                                .setUser(new User().setUserName("TEST USER 4"))
                                .setDateOfJob(new SimpleDate(2019, 11, 2));
                        job5.setJobTitle("This is a great new opportunity")
                                .setJobDescription("smoking on that oooweeeee")
                                .setUser(new User().setUserName("thetoker420"))
                                .setDateOfJob(new SimpleDate(2010, 1, 2));
                        job6.setJobTitle("Another Great opportunity")
                                .setJobDescription("blow blow bloaw jah rastafari real hip hop")
                                .setUser(new User().setUserName("TEST USER 4"))
                                .setDateOfJob(new SimpleDate(2019, 9, 2));
                        job7.setJobTitle("Come to our organic farm and boof deemsters")
                                .setJobDescription("blow blow bloaw jah rastafari real hip hop")
                                .setUser(new User().setUserName("whattup bull"))
                                .setDateOfJob(new SimpleDate(2019, 12, 2));

                        testJobs.add(job1);
                        testJobs.add(job2);
                        testJobs.add(job3);
                        testJobs.add(job4);
                        testJobs.add(job5);
                        testJobs.add(job6);
                        testJobs.add(job7);

                        Bundle args = new Bundle();
                        args.putSerializable("job_list", testJobs);
                        jobListFragment = new JobListFragment();
                        jobListFragment.setArguments(args);
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, jobListFragment).commit();
                        break;
                    case R.id.nav_map:
                        mapFragment = new MapFragment();
                        mapFragment.setArguments(mapTestBundle);
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
        Intent jobViewIntent = new Intent(MainActivity.this, JobViewActivity.class);
        jobViewIntent.putExtra("this_job", selectedJob);
        startActivity(jobViewIntent);
    }
}