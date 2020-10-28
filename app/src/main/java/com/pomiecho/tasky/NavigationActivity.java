package com.pomiecho.tasky;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.pomiecho.tasky.ui.todo.ToDoFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_to_do, R.id.nav_in_progress, R.id.nav_done, R.id.nav_task)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(
                this,
                R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this,
                navController,
                mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setCurrentDateOnNavigationView(navigationView.getHeaderView(0),
                getCurrentDay(),
                getCurrentDate());
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void setCurrentDateOnNavigationView(View navigationView, String day, String date) {
        TextView dayTextView = (TextView) navigationView.findViewById(R.id.dayTextView);
        TextView dateTextView = (TextView) navigationView.findViewById(R.id.dateTextView);
        dayTextView.setText(day);
        dateTextView.setText(date);
    }

    public String getCurrentDay() {
        Date currentDay = Calendar.getInstance().getTime();
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(currentDay.getTime());
    }

    public String getCurrentDate() {
        Date currentDay = Calendar.getInstance().getTime();
        return new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                .format(currentDay.getTime());
    }
}