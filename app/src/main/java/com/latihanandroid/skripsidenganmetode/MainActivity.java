package com.latihanandroid.skripsidenganmetode;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.latihanandroid.skripsidenganmetode.controller.DailyScheduleListController;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tuesday, R.id.nav_wednesday, R.id.nav_thursday,R.id.nav_monday,R.id.nav_friday,R.id.nav_saturday,R.id.nav_sunday,R.id.nav_roles_and_goals)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(this);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return
                NavigationUI.navigateUp(navController, mAppBarConfiguration)
                ||
                super.onSupportNavigateUp()
                ;
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        switch (destination.getId()){
            case R.id.nav_monday:
                DailyScheduleListController.hariValue= Calendar.MONDAY;
                break;
            case R.id.nav_tuesday:
                DailyScheduleListController.hariValue= Calendar.TUESDAY;
                break;
            case R.id.nav_wednesday:
                DailyScheduleListController.hariValue=Calendar.WEDNESDAY;
                break;
            case R.id.nav_thursday:
                DailyScheduleListController.hariValue=Calendar.THURSDAY;
                break;
            case R.id.nav_friday:
                DailyScheduleListController.hariValue=Calendar.FRIDAY;
                break;
            case R.id.nav_saturday:
                DailyScheduleListController.hariValue= Calendar.SATURDAY;
                break;
            case R.id.nav_sunday:
                DailyScheduleListController.hariValue=Calendar.SUNDAY;
                break;
        }

    }

}
