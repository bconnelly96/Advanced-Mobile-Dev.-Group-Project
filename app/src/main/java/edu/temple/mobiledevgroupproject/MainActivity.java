package edu.temple.mobiledevgroupproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import edu.temple.mobiledevgroupproject.Objects.Comment;
import edu.temple.mobiledevgroupproject.Objects.Constants;
import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.Objects.Record;
import edu.temple.mobiledevgroupproject.Objects.RequestHandler;
import edu.temple.mobiledevgroupproject.Objects.SharedPrefManager;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.SimpleTime;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.UI.FormFragment;
import edu.temple.mobiledevgroupproject.UI.JobListFragment;
import edu.temple.mobiledevgroupproject.UI.JobViewActivity;
import edu.temple.mobiledevgroupproject.UI.LogInActivity;
import edu.temple.mobiledevgroupproject.UI.MapFragment;
import edu.temple.mobiledevgroupproject.UI.ProfileFragment;

public class MainActivity extends AppCompatActivity implements JobListFragment.JobSelectedInterface, MapFragment.MapClickInterface, FormFragment.FormInterface {
    //layout objects
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ImageView profImg;
    //fragment objects
    private FragmentManager fragmentManager;
    private ProfileFragment profileFragment;
    private FormFragment formFragment;
    private JobListFragment jobListFragment;
    private MapFragment mapFragment;
    //other objects
    User thisUser;
    LocationManager locationManager;
    LocationListener locationListener;

    ArrayList<Job> jobsToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            jobsToDisplay = new ArrayList<>();
            thisUser = new User();
            thisUser.setName(SharedPrefManager.getInstance(this).getName());
            thisUser.setUserName(SharedPrefManager.getInstance(this).getUserName());
            thisUser.setUserBirthDay(new SimpleDate(SharedPrefManager.getInstance(this).getBirthday()));
            thisUser.setPreviousJobs(new Record("Dummy Previous Job Record", "job"));
            thisUser.setUserRating(SharedPrefManager.getInstance(this).getRating());

            getJobs(getApplicationContext());


        } else {
            finish();
            startActivity(new Intent(this, LogInActivity.class));
        }


        //initialize layout objects
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.app_bar);
        //set up toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        navigationView = findViewById(R.id.nav_view);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng currUserPos = new LatLng(latitude, longitude);
                startMapFragment(currUserPos);
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.d("status_changed", "Status Changed");
            }
            @Override
            public void onProviderEnabled(String provider) {
                Log.d("provider", "Provider Enabled");
            }
            @Override
            public void onProviderDisabled(String provider) {
                Log.d("provider", "Provider Disabled");
            }
        };

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);

        //place MapFragment as first one within the main container
        fragmentManager = getSupportFragmentManager();
        mapFragment = new MapFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();

        //listen for nav. item selected events
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        //launch different fragments depending on selected nav. menu item
                        switch (menuItem.getItemId()) {
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
                                jobListFragment = new JobListFragment();
                                Bundle args4 = new Bundle();
                                args4.putParcelableArrayList("job_list", jobsToDisplay);
                                jobListFragment.setArguments(args4);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, jobListFragment).commit();
                                break;
                            case R.id.nav_map:
                                mapFragment = new MapFragment();
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

    //handle user input on the nav. bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            //refresh button
            case R.id.action_refresh:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);

                //set nav. header profile image and username
                TextView headerUsername = navigationView.findViewById(R.id.nav_user);
                headerUsername.setText("@" + thisUser.getUserName());

                profImg = navigationView.findViewById(R.id.nav_prof_img);
                profImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });

                String userProfImg;
                if ((userProfImg = User.fetchProfImg(getFilesDir())) != null) {
                    Bitmap bitmap = User.decodeToBitmap(userProfImg);
                    profImg.setImageBitmap(bitmap);
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Implements JobListFragment.JobSelectedInterface
     * Launches JobViewActivity passing in selectedJob from list as arg.
     */
    @Override
    public void getSelectedJob(Job selectedJob) {
        launchJobViewActivity(selectedJob);
    }

    /**
     * Implements MapFragment.MapClickInterface
     * Launches JobViewAcitivty passing in job selected from map as arg.
     */
    @Override
    public void jobSelected(Job selectedJob) {
        launchJobViewActivity(selectedJob);
    }

    /**
     * Implements FormFragment.FormInterface
     * @param jobData The Job object created after the successful retrieval of data from user.
     * @param user The user who created the job.
     */
    @Override
    public void getDataFromForm(Job jobData, User user) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
        jobsToDisplay.add(jobData);
        launchJobViewActivity(jobData);
    }

    /**
     * Receive selected image from image selecting activity.
     * Set user's profile image as selected image.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                profImg.setImageBitmap(bitmap);
                User.writeProfImg(User.encodeToString(bitmap), getFilesDir());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Receive results of permission request.
     * Request location updates if permission to access location is granted.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 10, locationListener);
            }
        }
    }

    /**
     * Helper method.
     * Starts intent to open phone's gallery for profile image selection.
     */
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 100);
    }


    /**
     * Helper method.
     * Creates a new Intent for JobViewActivity.java
     * Adds selectedJob and thisUser as extra data.
     * @param selectedJob The selected job from the ListFragment or MapFragment.
     */
    private void launchJobViewActivity(Job selectedJob) {
        Intent jobViewIntent = new Intent(this, JobViewActivity.class);
        jobViewIntent.putExtra("this_job", selectedJob);
        jobViewIntent.putExtra("this_user", thisUser);
        startActivity(jobViewIntent);
    }

    /**
     * Helper method.
     * Retrieves user's current position from the Location Listener.
     * Passes the location in a bundle to a new MapFragment instance
     * Places new instance in fragment container.
     * @param userCurrentPosition The user's current position on the map as specified by the location updates.
     */
    private void startMapFragment(LatLng userCurrentPosition) {
        fragmentManager = getSupportFragmentManager();
        mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER_POSITION", userCurrentPosition);
        bundle.putSerializable("jobs_to_display", jobsToDisplay);
        mapFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
    }

    //Get the jobs from the database and store them in an arraylist
    private void getJobs(Context context){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.GET_JOBS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        //Mechanism to wait for job data to load prior to handling UI
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.d("JobResponse", jsonObject.toString());

                                    if(!jsonObject.getBoolean("error")){
                                        JsonArray jobListArr = new JsonParser().parse(jsonObject.getString("jobs")).getAsJsonArray();
                                        //Log.d("JobJsonArr", jobListArr.getAsString());

                                        //Parse JsonArray into job objects for ListView
                                        for(int i = 0; i < jobListArr.size(); i++) {
                                            JsonObject tempJson = jobListArr.get(i).getAsJsonObject();
                                            Job tempJob = new Job();
                                            tempJob.setJobTitle(tempJson.get("jobTitle").getAsString());
                                            tempJob.setJobDescription(tempJson.get("jobDescription").getAsString());
                                            tempJob.setDatePosted(new SimpleDate(tempJson.get("datePosted").getAsString()));
                                            tempJob.setDateOfJob(new SimpleDate(tempJson.get("dateOfJob").getAsString()));
                                            tempJob.setStartTime(new SimpleTime(tempJson.get("startTime").getAsString()));
                                            tempJob.setEndTime(new SimpleTime(tempJson.get("endTime").getAsString()));
                                            tempJob.setLocation(new LatLng(Double.valueOf(tempJson.get("latitude").getAsString()), Double.valueOf(tempJson.get("longitude").getAsString())));
                                            Log.d("JobToAdd", tempJob.toString());
                                            //TODO figure out how to generate user from username
                                            tempJob.setUser(new User());
                                            //TODO figure out comments
                                            tempJob.setCommentList(new Record<Comment>("TestComment", "Comment"));
                                            jobsToDisplay.add(tempJob);
                                        }

                                        Log.d("JobList", jobsToDisplay.toString());
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                        Log.d("JobResponse", "Error getting jobs");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){
        };
        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}