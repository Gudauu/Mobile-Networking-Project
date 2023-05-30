package com.example.wewallhere.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.wewallhere.User.PhoneVerificationActivity;
import com.example.wewallhere.R;
import com.example.wewallhere.Upload.UploadActivity;
import com.example.wewallhere.ExploreByList.ExploreListActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonToUploadSection;
    private Button buttonToExploreList;
    private VideoView tempVideo;
    private int REQUEST_ALL = 200;
    private int REQUEST_EXPLORE_ACTIVITY = 1022;
    private final String [] all_permissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user has logged in before
        boolean isLoggedIn = checkLoggedIn();

        // If not logged in or last login was more than 30 days ago, redirect to PhoneVerificationActivity
        if (!isLoggedIn) {
            startActivity(new Intent(MainActivity.this, PhoneVerificationActivity.class));
            finish(); // Optional: Finish the MainActivity so that the user cannot go back to it without verification
        }

        // Continue with rendering the MainActivity page
        setContentView(R.layout.activity_main);




        buttonToUploadSection = findViewById(R.id.go_to_upload_section);
        buttonToUploadSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, UploadActivity.class);
                startActivity(intent);
            }
        });
        buttonToExploreList = findViewById(R.id.go_to_view_media_list);
        buttonToExploreList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSingleLocationPermission()) {
                    goToExploreListActivity();
                } else {
                    // Request the necessary permissions
                    String[] permissions = new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    };
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_EXPLORE_ACTIVITY);
                }

            }
        });


        ActivityCompat.requestPermissions(this, all_permissions, REQUEST_ALL);



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXPLORE_ACTIVITY) {
            // Check if all required permissions are granted, including media & location
            boolean allPermissionsGranted = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }
            if (allPermissionsGranted) {
                goToExploreListActivity();
            } else {
                Toast.makeText(this, "Permissions not granted.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean checkLoggedIn() {
        // Retrieve the last login date from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("INFO", MODE_PRIVATE);
        long lastLoginTimestamp = sharedPreferences.getLong("lastLogin", 0);

        // Check if the last login is within 30 days from now
        long currentTimestamp = System.currentTimeMillis();
        long thirtyDaysInMillis = 30 * 24 * 60 * 60 * 1000L;
        boolean isWithinThirtyDays = (currentTimestamp - lastLoginTimestamp) <= thirtyDaysInMillis;

        return isWithinThirtyDays;
    }

    // go to main page when scrolling back
    @Override
    public void onBackPressed() {
        // Start the main activity or perform any other navigation action
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all previous activities
        startActivity(intent);
    }
    private void goToExploreListActivity(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ExploreListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all activities on top of ListViewActivity
        startActivity(intent);
    }

    private boolean checkSingleLocationPermission(){
        int fine_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarse_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        return fine_location == PackageManager.PERMISSION_GRANTED &&
                coarse_location == PackageManager.PERMISSION_GRANTED;
    }
}