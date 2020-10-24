package com.pomiecho.tasky.ui.todo;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomiecho.tasky.R;
import com.pomiecho.tasky.adapters.TaskAdapter;
import com.pomiecho.tasky.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {

    private ToDoModel toDoModel;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private List<Task> taskList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toDoModel = new ViewModelProvider(this).get(ToDoModel.class);
        View root = inflater.inflate(R.layout.fragment_to_do, container, false);

        recyclerView = root.findViewById(R.id.recyclerViewToDo);
        taskList = new ArrayList<>();
        adapter = new TaskAdapter(getActivity(), taskList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareTasks();

        toDoModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        return root;
    }

    private void prepareTasks() {
        Task a = new Task("Lorem ipsum", "Dolor sit amet..");
        taskList.add(a);

        Task b = new Task("Lorem ipsum 2", "Dolor sit amet..");
        taskList.add(b);

        Task c = new Task("Lorem ipsum 3", "Dolor sit amet..");
        taskList.add(c);

        Task d = new Task("Lorem ipsum 4", "Dolor sit amet..");
        taskList.add(d);

        adapter.notifyDataSetChanged();
    }

}