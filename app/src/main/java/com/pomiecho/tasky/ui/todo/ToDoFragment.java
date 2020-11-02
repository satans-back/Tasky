package com.pomiecho.tasky.ui.todo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pomiecho.tasky.R;
import com.pomiecho.tasky.SQLiteHandler;
import com.pomiecho.tasky.adapters.ToDoAdapter;
import com.pomiecho.tasky.interfaces.CardClickListener;
import com.pomiecho.tasky.interfaces.Communicator;
import com.pomiecho.tasky.objects.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ToDoFragment extends Fragment {

    public SQLiteHandler db;
    private ToDoModel toDoModel;
    private RecyclerView recyclerView;
    private ToDoAdapter adapter;
    private List<Task> taskList;

    public Communicator communicator;
    public CardClickListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toDoModel = new ViewModelProvider(this).get(ToDoModel.class);
        View root = inflater.inflate(R.layout.fragment_to_do, container, false);
        db = new SQLiteHandler(getActivity());
        recyclerView = root.findViewById(R.id.recycler_to_do);
        taskList = new ArrayList<>();

        adapter = new ToDoAdapter(getActivity(), taskList, new CardClickListener() {
            @Override
            public void inProgressClick(View v, int position) {
                communicator.setTaskInProgress(taskList.get(position));
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void doneClick(View v, int position) {
                communicator.setTaskDone(taskList.get(position));
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void deleteClick(View v, int position) {
                db.deleteTask(taskList.get(position));
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void toDoClick(View v, int position) { }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareTasks();

        toDoModel.getText().observe(getViewLifecycleOwner(), s -> { });
        return root;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        Activity navigationActivity = getActivity();
        try {
            if(context instanceof Activity)
                communicator = (Communicator) navigationActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException(navigationActivity.toString());
        }
    }

    public void updateRecyclerView() {
        adapter.notifyDataSetChanged();
    }

    public void prepareTasks() {
        taskList.clear();
        taskList.addAll(db.getTasks(1));
        updateRecyclerView();
    }

    public void addNewTask(Task task) {
        db.insertTask(task);
        taskList.clear();
        prepareTasks();
    }

    public void addToDoTask(Task task) {
        task.setState(1);
        db.updateTask(task);
        taskList.clear();
        prepareTasks();
    }

}