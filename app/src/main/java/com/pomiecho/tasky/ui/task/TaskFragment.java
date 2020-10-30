package com.pomiecho.tasky.ui.task;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pomiecho.tasky.R;

public class TaskFragment extends Fragment {

    private TaskModel mViewModel;
    private Button cancelButton;
    private Button applyCreateButton;

    public static TaskFragment newInstance() {
        return new TaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        cancelButton = getActivity().findViewById(R.id.frag_cancel_button);
        applyCreateButton = getActivity().findViewById(R.id.frag_apply_create_button);

        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TaskModel.class);
    }

}