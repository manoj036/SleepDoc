package com.example.manojkumar.practiceui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.manojkumar.practiceui.ble.DeviceListActivity;
import com.firebase.ui.auth.AuthUI;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationViewEx.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private BottomNavigationViewEx bnve;
    private TextView currentUser;
    public static int CURRENT_BTM_ACTIVITY =R.id.sleep_summary_button;
    public static int current_base_activity=1;
    public NavigationView navigationView;

    public static final String TAG ="BASE_ACTIVITY";
    public static final int REQUEST_ENABLE_BT=13466;
    public static final int REQUEST_ENABLE_COARSE_LOCATION=13467;
    public static final int REQUEST_ENABLE_FINE_LOCATION=13468;
    public static final int REQUEST_ENABLE_RECORD_AUDIO=13469;
    private static final int REQUEST_EXTERNAL_STORAGE = 13464;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        getWindow().setBackgroundDrawable(null);
        android.support.v7.widget.Toolbar toolbar= findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            Drawable drawable= getResources().getDrawable(R.drawable.menu);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 80, 50, true));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(newdrawable);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        TextView datetv=findViewById(R.id.todays_date);
        if(datetv!=null) {
            Calendar calendar = Calendar.getInstance();
            Date d = calendar.getTime();
            String day = (String) DateFormat.format("EEEE", d);
            String month = (String) DateFormat.format("MMM", d);
            String date = (String) DateFormat.format("dd", d);
            String dateDisplay = day + ", " + month + " " + date;
            datetv.setText(dateDisplay);
        }

        drawerLayout=findViewById(R.id.drawerLayout);

        navigationView=findViewById(R.id.nav_drawer_view);

        currentUser=findViewById(R.id.currentUser);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_sleep_summary:
                    if(!navigationView.getMenu().getItem(1).isChecked()) {
                        startActivity(new Intent(getApplicationContext(), SleepSummaryActivity.class));
					}
                    break;
                case R.id.nav_logout:
                    AuthUI.getInstance()
                            .signOut(getApplicationContext())
                            .addOnCompleteListener(task -> startActivity(new Intent(getApplicationContext(),SignupActivity.class)));
                    break;
                case R.id.nav_connect:
                    startActivity(new Intent(getApplicationContext(),DeviceListActivity.class));
                    break;
				case R.id.nav_smart_alarm:
					startActivity(new Intent(getApplicationContext(),SmartAlarmActivity.class));
					navigationView.getMenu().getItem(3).setChecked(true);
					break;
            }
            drawerLayout.closeDrawers();
            return true;
        });
        bnve =findViewById(R.id.bottomBar);
        if(bnve!=null) {
			bnve.setOnNavigationItemSelectedListener(this);
			bnve.enableShiftingMode(false);
			bnve.enableItemShiftingMode(false);
			bnve.enableAnimation(false);
			bnve.setTextVisibility(false);
		}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.users,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
		if(bnve!=null) {
			bnve.setSelectedItemId(getNavigationMenuItemId());
		}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()!= CURRENT_BTM_ACTIVITY){
            Log.d(TAG, "onNavigationItemSelected: "+item.getItemId());
            bnve.postDelayed(()->{
                switch (item.getItemId()){
                    case R.id.sleep_summary_button:
//                        startActivity(new Intent(getApplicationContext(), SleepSummaryActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                        startActivity(new Intent(getApplicationContext(),SleepSummaryActivity.class));
                        break;
                    case R.id.respiration_button:
//                        startActivity(new Intent(getApplicationContext(), RespirationActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                        startActivity(new Intent(getApplicationContext(),RespirationActivity.class));
                        break;
                    case R.id.heart_rate_button:
//                        startActivity(new Intent(getApplicationContext(),HeartRateActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                        startActivity(new Intent(getApplicationContext(),HeartRateActivity.class));
                        break;
                    case R.id.live_monitoring_button:
//                        startActivity(new Intent(getApplicationContext(),LiveMonitoringActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                        startActivity(new Intent(getApplicationContext(),LiveMonitoringActivity.class));
                        break;
                    case R.id.environment_button:
//                        startActivity(new Intent(getApplicationContext(),EnvironmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                        startActivity(new Intent(getApplicationContext(),EnvironmentActivity.class));
                        break;
                }
                //                CURRENT_BTM_ACTIVITY=item.getItemId();
            },0);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.user1:
                currentUser.setText(item.getTitle());
                return true;
            case R.id.user2:
                currentUser.setText(item.getTitle());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public abstract int getContentViewId();

    public abstract int getNavigationMenuItemId();

    private void checkPermissions() {
        verifyCoarseLocationPermissions(BaseActivity.this);
        verifyFineLocationPermissions(BaseActivity.this);
        verifyRecorderPermissions(BaseActivity.this);
        verifyStoragePermissions(BaseActivity.this);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public static void verifyCoarseLocationPermissions(Activity activity){
        int permisssion= ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_COARSE_LOCATION);

        if (permisssion!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission. ACCESS_COARSE_LOCATION},REQUEST_ENABLE_COARSE_LOCATION);
        }
    }

    public static void verifyFineLocationPermissions(Activity activity){
        int permisssion= ActivityCompat.checkSelfPermission(activity,Manifest.permission.ACCESS_FINE_LOCATION);
        if (permisssion!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission. ACCESS_FINE_LOCATION},REQUEST_ENABLE_FINE_LOCATION);
        }
    }

    public static void verifyRecorderPermissions(Activity activity){
        int permission= ActivityCompat.checkSelfPermission(activity,Manifest.permission.RECORD_AUDIO);
        if (permission!=PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_ENABLE_RECORD_AUDIO);
    }

}
