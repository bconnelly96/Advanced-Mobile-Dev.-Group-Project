package edu.temple.mobiledevgroupproject;

import android.Manifest;
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

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent recIntent = getIntent();
        if (recIntent != null) {
            thisUser = recIntent.getParcelableExtra("this_user");
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
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        //listen for nav. item selected events
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        //launch different fragments depending on selected nav. menu item
                        switch (menuItem.getItemId()) {
                            case R.id.nav_profile:
                                fragmentManager = getSupportFragmentManager();
                                profileFragment = new ProfileFragment();
                                Bundle args2 = new Bundle();
                                args2.putParcelable("user_to_display", thisUser);
                                profileFragment.setArguments(args2);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
                                break;
                            case R.id.nav_postjob:
                                fragmentManager = getSupportFragmentManager();
                                formFragment = new FormFragment();
                                Bundle args3 = new Bundle();
                                args3.putParcelable("this_user", thisUser);
                                formFragment.setArguments(args3);
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, formFragment).commit();
                                break;
                            case R.id.nav_joblist:
                                fragmentManager = getSupportFragmentManager();
                                jobListFragment = new JobListFragment();
                                fragmentManager.beginTransaction().replace(R.id.fragment_container, jobListFragment).commit();
                                break;
                            case R.id.nav_map:
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
        //TODO update user's job lists
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
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
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
        mapFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment).commit();
    }
}