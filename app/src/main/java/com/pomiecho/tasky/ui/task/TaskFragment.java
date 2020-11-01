package com.pomiecho.tasky.ui.task;

import androidx.lifecycle.ViewModelProvider;

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
import com.pomiecho.tasky.objects.Task;

import org.jetbrains.annotations.NotNull;

public class TaskFragment extends Fragment {

    public SQLiteHandler db;
    private  Task task;
    private TaskModel taskModel;

    private Button cancelButton;
    private Button applyCreateButton;
    private TextInputLayout taskTitleText;
    private TextInputLayout taskDescriptionText;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        cancelButton = getActivity().findViewById(R.id.frag_cancel_button);
        applyCreateButton = getActivity().findViewById(R.id.frag_create_button);
        taskTitleText = getActivity().findViewById(R.id.frag_task_title);
        taskDescriptionText = getActivity().findViewById(R.id.frag_task_desc);

        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        taskModel = new ViewModelProvider(this).get(TaskModel.class);
    }

    public void buttonClicked(@NotNull View v) {
        switch(v.getId()) {
            case R.id.frag_cancel_button:
                taskTitleText.getEditText().getText().clear();
                taskDescriptionText.getEditText().getText().clear();
                Navigation.findNavController(v).navigate(R.id.action_nav_task_to_nav_to_do);
                break;
            case R.id.frag_create_button: {
                Navigation.findNavController(v).navigate(R.id.action_nav_task_to_nav_to_do);
                break;
            }
        }
    }

}