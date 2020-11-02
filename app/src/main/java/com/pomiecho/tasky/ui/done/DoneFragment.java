package com.pomiecho.tasky.ui.done;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import com.pomiecho.tasky.adapters.DoneAdapter;
import com.pomiecho.tasky.adapters.InProgressAdapter;
import com.pomiecho.tasky.interfaces.CardClickListener;
import com.pomiecho.tasky.interfaces.Communicator;
import com.pomiecho.tasky.objects.Task;
import com.pomiecho.tasky.ui.todo.ToDoModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoneFragment extends Fragment {

    private SQLiteHandler db;
    private DoneModel doneModel;
    private RecyclerView recyclerView;
    private DoneAdapter adapter;
    private List<Task> taskList;

    public Communicator communicator;
    public CardClickListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        doneModel = new ViewModelProvider(this).get(DoneModel.class);
        new ViewModelProvider(this).get(DoneModel.class);
        View root = inflater.inflate(R.layout.fragment_done, container, false);

        db = new SQLiteHandler(getActivity());
        recyclerView = root.findViewById(R.id.recycler_done);
        taskList = new ArrayList<>();

        adapter = new DoneAdapter(getActivity(), taskList, new CardClickListener() {
            @Override
            public void toDoClick(View v, int position) {
                communicator.setTaskToDo(taskList.get(position));
                taskList.remove(position);
                adapter.notifyItemRemoved(position);
            }

            @Override
            public void inProgressClick(View v, int position) {
                communicator.setTaskInProgress(taskList.get(position));
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
            public void doneClick(View v, int position) { }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        for(Task task : db.getTasks(3)) {
            taskList.add(task);
        }

        updateRecyclerView();

        doneModel.getText().observe(getViewLifecycleOwner(), s -> { });
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
        taskList.addAll(db.getTasks(3));
        updateRecyclerView();
    }

    public void addDoneTask(Task task) {
        task.setState(3);
        db.updateTask(task);
        prepareTasks();
    }
}