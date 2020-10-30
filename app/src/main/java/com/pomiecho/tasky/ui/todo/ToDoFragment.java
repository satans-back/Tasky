package com.pomiecho.tasky.ui.todo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomiecho.tasky.R;
import com.pomiecho.tasky.SQLiteHandler;
import com.pomiecho.tasky.adapters.ToDoAdapter;
import com.pomiecho.tasky.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {

    public SQLiteHandler db;
    private ToDoModel toDoModel;
    private RecyclerView recyclerView;
    private ToDoAdapter adapter;
    private List<Task> taskList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toDoModel = new ViewModelProvider(this).get(ToDoModel.class);
        View root = inflater.inflate(R.layout.fragment_to_do, container, false);
        db = new SQLiteHandler(getActivity());
        recyclerView = root.findViewById(R.id.recycler_to_do);
        taskList = new ArrayList<>();
        adapter = new ToDoAdapter(getActivity(), taskList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareTasks();

        toDoModel.getText().observe(getViewLifecycleOwner(), s -> { });
        return root;
    }

    public void updateRecyclerView() {
        adapter.notifyDataSetChanged();
    }

    public void prepareTasks() {
        taskList.addAll(db.getTasks(1));
        updateRecyclerView();
    }

    public void addNewTask(Task task) {
        db.insertTask(task);
        taskList.clear();

        prepareTasks();
    }

    public void addToDoTask(Task task) {
        db.updateTask(task);
        taskList.clear();

        prepareTasks();
    }

}