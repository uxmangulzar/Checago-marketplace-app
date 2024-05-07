package com.softarena.checagocoffee.Acitivity.Splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.softarena.checagocoffee.Acitivity.Home.MainMenuDrawerActivity;
import com.softarena.checagocoffee.Acitivity.Signin.LoginActivity;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.R;

import im.delight.android.location.SimpleLocation;

public class SplashActivity extends AppCompatActivity {
    SharedPreferences walkthrough;
    private SimpleLocation location;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    boolean onResumeCalled = true;
    @Override
    protected void onResume() {
        super.onResume();
        if(!onResumeCalled){
            if (ContextCompat.checkSelfPermission(SplashActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                checkPermissions();
            }else {
                if (location.hasLocationEnabled()) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            walkthrough = getSharedPreferences("Walkthrough",MODE_PRIVATE);
                            boolean isFirstTime = walkthrough.getBoolean("first time", true);
                            if(isFirstTime )
                            {
                                SharedPreferences.Editor editor =   walkthrough.edit();
                                editor.putBoolean("first time", false);
                                editor.commit();
                                Intent intent = new Intent(SplashActivity.this, WalkthroughActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {

                                final String userName = SessionManager.getStringPref(HelperKeys.USER_NAME, getApplicationContext());
                                if (!userName.equals("")){
                                    Intent intent = new Intent(SplashActivity.this, MainMenuDrawerActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }





                        }
                    },3000);//screen timeout time of splash
                }else {
                    SimpleLocation.openSettings(this);
                }
            }
        }else {
            onResumeCalled=false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        location = new SimpleLocation(this);

        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            checkPermissions();
        }else {
            if (location.hasLocationEnabled()) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        walkthrough = getSharedPreferences("Walkthrough",MODE_PRIVATE);
                        boolean isFirstTime = walkthrough.getBoolean("first time", true);
                        if(isFirstTime )
                        {
                            SharedPreferences.Editor editor =   walkthrough.edit();
                            editor.putBoolean("first time", false);
                            editor.commit();
                            Intent intent = new Intent(SplashActivity.this, WalkthroughActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {

                            final String userName = SessionManager.getStringPref(HelperKeys.USER_NAME, getApplicationContext());
                            if (!userName.equals("")){
                                Intent intent = new Intent(SplashActivity.this, MainMenuDrawerActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }





                    }
                },3000);//screen timeout time of splash
            }else {
                SimpleLocation.openSettings(this);
            }
        }

    }
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(SplashActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED)
                requestLocationPermission();
            else{
                if (!location.hasLocationEnabled()) {
                    // ask the user to enable location access
                    SimpleLocation.openSettings(this);
                }
            }

        } else
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

    }
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ACCESS_FINE_LOCATION_INTENT_ID: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (!location.hasLocationEnabled()) {
                        // ask the user to enable location access
                        SimpleLocation.openSettings(this);
                    }

                } else {

                    if (!location.hasLocationEnabled()) {
                        // ask the user to enable location access
                        SimpleLocation.openSettings(this);
                    }
                }
                return;
            }
        }
    }
}
