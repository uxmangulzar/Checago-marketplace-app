package com.softarena.checagocoffee.Acitivity.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.softarena.checagocoffee.Acitivity.Cart.CartActivity;
import com.softarena.checagocoffee.Acitivity.Scanning.ScanActivity;
import com.softarena.checagocoffee.Acitivity.Signin.LoginActivity;
import com.softarena.checagocoffee.Acitivity.Profile.EditProfileFragment;
import com.softarena.checagocoffee.Acitivity.Notifications.NotificationFragment;
import com.softarena.checagocoffee.Acitivity.Orders.OrderHistoryActivity;
import com.softarena.checagocoffee.Helper.DatabaseHelper;
import com.softarena.checagocoffee.Helper.HelperKeys;
import com.softarena.checagocoffee.Helper.SessionManager;
import com.softarena.checagocoffee.Acitivity.Products.AllProduct;
import com.softarena.checagocoffee.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import im.delight.android.location.SimpleLocation;

public class MainMenuDrawerActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 3;
    ActionBarDrawerToggle toggle;
    ImageView imageView_close_nav;
    BottomNavigationView main_nav;
    FrameLayout main_fram;
    ImageView img_chat,img_cart;
    TextView tv_toolBar;
    TextView cart_items;
    View menuHome,menuNotification,menuLogout,menuContactUs,menuAboutUs,menuEditProfile,menuOrderHistory,menuqr;
    //calling fragments and their constructors for the navbar
    final MainMenuFragment mainMenuFragment = new MainMenuFragment();
    final EditProfileFragment editProfileFragment = new EditProfileFragment();
    final NotificationFragment notificationFragment = new NotificationFragment();

    final OrderHistoryActivity orderHistoryFragment = new OrderHistoryActivity();

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;
    private SimpleLocation location;
    private DatabaseHelper databaseHelper;


    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainMenuDrawerActivity.this,
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainMenuDrawerActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainMenuDrawerActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(MainMenuDrawerActivity.this,
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_drawer);
         databaseHelper=new DatabaseHelper(getApplicationContext());

        initViews();
        location = new SimpleLocation(this);

        checkPermissions();

        setFragment(new MainMenuFragment());
        setSupportActionBar(toolbar);
        toolbar.setTitle("Gizah");
        tv_toolBar.setText("Gizah");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setTitle("Gizah");


        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        img_cart.setVisibility(View.VISIBLE);
        cart_items.setText(databaseHelper.getItemsincart()+"");

        menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle("Gizah");
                tv_toolBar.setText("Gizah");
                img_cart.setVisibility(View.VISIBLE);
                setFragment(mainMenuFragment);
            }
        });
        menuEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle("Edit Profile");
                tv_toolBar.setText("Edit Profile");
                setFragment(editProfileFragment);
                img_cart.setVisibility(View.VISIBLE);
            }
        });



        menuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDatabase("chicagocoffee");
                drawerLayout.closeDrawer(GravityCompat.START);
                SessionManager.putStringPref(HelperKeys.USER_NAME, "", MainMenuDrawerActivity.this);
                SessionManager.putStringPref(HelperKeys.User_Access_Token, "", MainMenuDrawerActivity.this);
                SessionManager.putStringPref(HelperKeys.USER_ID, "", MainMenuDrawerActivity.this);
                SessionManager.putStringPref(HelperKeys.User_Type, "", MainMenuDrawerActivity.this);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        menuNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle("Notifications");
                tv_toolBar.setText("Notifications");
                img_cart.setVisibility(View.VISIBLE);

                setFragment(notificationFragment);
            }
        });
        menuOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle("Order History");
                tv_toolBar.setText("Order History");
                //setFragment(orderHistoryFragment);
                Intent intent=new Intent(getApplicationContext(),OrderHistoryActivity.class);
                startActivity(intent);
            }
        });
        menuqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle("Gizah");
                tv_toolBar.setText("Gizah");
                //setFragment(orderHistoryFragment);
                Intent intent=new Intent(getApplicationContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
       /* imageView_close_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START,false);
            }
        });*/
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AllProduct> allProductsList=databaseHelper.getAllData();
                if (allProductsList.size()!=0){
                    Intent intent = new Intent(MainMenuDrawerActivity.this, CartActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainMenuDrawerActivity.this, "cart is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void initViews()
    {
        img_cart = findViewById(R.id.img_cart);
        cart_items = findViewById(R.id.cart_items);
        drawerLayout = findViewById(R.id.drawer);
        toolbar =  findViewById(R.id.toobarnav);
        main_fram = findViewById(R.id.main_fram);
        imageView_close_nav = findViewById(R.id.imageView_close_nav);
        navigationView = findViewById(R.id.navigation_viewnew);
        menuHome = findViewById(R.id.menuHome);
        menuEditProfile = findViewById(R.id.menuEditProfile);
        menuLogout = findViewById(R.id.menuLogout);
        menuContactUs = findViewById(R.id.menuContactUs);
        menuAboutUs =  findViewById(R.id.menuAboutUs);
        menuNotification = findViewById(R.id.menuNotification);
        menuOrderHistory = findViewById(R.id.menuOrderHistory);
        menuqr = findViewById(R.id.menuqr);
        tv_toolBar = findViewById(R.id.tv_toolBar);
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fram,fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // make the device update its location
        location.beginUpdates();
        cart_items.setText(databaseHelper.getItemsincart()+"");
        if(getIntent().getExtras() != null) {
            getSupportActionBar().setTitle("Notifications");
            tv_toolBar.setText("Notifications");
            img_cart.setVisibility(View.VISIBLE);

            setFragment(notificationFragment);
        }
    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();

        // ...

        super.onPause();
    }

}