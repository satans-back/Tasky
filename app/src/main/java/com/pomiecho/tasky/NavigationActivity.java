package com.pomiecho.tasky;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.pomiecho.tasky.interfaces.Communicator;
import com.pomiecho.tasky.objects.Task;
import com.pomiecho.tasky.ui.done.DoneFragment;
import com.pomiecho.tasky.ui.inprogress.InProgressFragment;
import com.pomiecho.tasky.ui.task.TaskFragment;
import com.pomiecho.tasky.ui.todo.ToDoFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NavigationActivity extends AppCompatActivity implements Communicator {

    private AppBarConfiguration mAppBarConfiguration;
    private ToDoFragment toDoFragment;
    private InProgressFragment inProgressFragment;
    private DoneFragment doneFragment;

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

        FragmentManager fragmentManager = getSupportFragmentManager();

        toDoFragment = (ToDoFragment) fragmentManager.findFragmentById(R.id.nav_to_do);
        if (toDoFragment == null) {
            toDoFragment = new ToDoFragment();
            fragmentManager.beginTransaction().add(toDoFragment, null).commit();
        }

        inProgressFragment = (InProgressFragment) fragmentManager.findFragmentById(R.id.nav_in_progress);
        if (inProgressFragment == null) {
            inProgressFragment = new InProgressFragment();
            fragmentManager.beginTransaction().add(inProgressFragment, null).commit();
        }

        doneFragment = (DoneFragment) fragmentManager.findFragmentById(R.id.nav_done);
        if (doneFragment == null) {
            doneFragment = new DoneFragment();
            fragmentManager.beginTransaction().add(doneFragment, null).commit();
        }

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
        TextView dayTextView = navigationView.findViewById(R.id.dayTextView);
        TextView dateTextView = navigationView.findViewById(R.id.dateTextView);
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            assert inputManager != null;
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void buttonClicked(@NotNull View v) {
        switch(v.getId()) {
            case R.id.frag_cancel_button:
                hideKeyboard(this);
                Navigation.findNavController(v).navigate(R.id.action_nav_task_to_nav_to_do);
                break;
            case R.id.frag_create_button: {
                hideKeyboard(this);
                TextInputLayout taskTitle =  findViewById(R.id.frag_task_title);
                TextInputLayout taskDesc = findViewById(R.id.frag_task_desc);
                if(TextUtils.isEmpty(taskTitle.getEditText().getText())
                    || TextUtils.isEmpty(taskDesc.getEditText().getText())) {
                    Toast.makeText(getApplicationContext(),
                            "Task title and description mustn't be empty",Toast.LENGTH_SHORT).show();
                    break;
                }
                createTask(new Task(taskTitle.getEditText().getText().toString(), taskDesc.getEditText().getText().toString()));
                Navigation.findNavController(v).navigate(R.id.action_nav_task_to_nav_to_do);
                break;
            }
        }
    }

    @Override
    public void createTask(Task task) {
        toDoFragment.addNewTask(task);
    }

    @Override
    public void setTaskToDo(Task task) { toDoFragment.addToDoTask(task); }

    @Override
    public void setTaskInProgress(Task task) { inProgressFragment.addInProgressTask(task); }

    @Override
    public void setTaskDone(Task task) { doneFragment.addDoneTask(task); }

    @Override
    public void deleteTask(Task task) { }
}