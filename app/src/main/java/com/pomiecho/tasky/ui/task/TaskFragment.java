package com.pomiecho.tasky.ui.task;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.pomiecho.tasky.R;
import com.pomiecho.tasky.SQLiteHandler;
import com.pomiecho.tasky.interfaces.Communicator;
import com.pomiecho.tasky.objects.Task;

import org.jetbrains.annotations.NotNull;

public class TaskFragment extends Fragment {

    private Task task;

    private TextInputLayout taskTitleText;
    private TextInputLayout taskDescriptionText;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TaskModel taskModel = new ViewModelProvider(this).get(TaskModel.class);
    }

    public void buttonClicked(@NotNull View v) { }

}