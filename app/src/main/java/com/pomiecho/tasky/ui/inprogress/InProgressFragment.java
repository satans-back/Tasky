package com.pomiecho.tasky.ui.inprogress;

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
import com.pomiecho.tasky.adapters.InProgressAdapter;
import com.pomiecho.tasky.adapters.ToDoAdapter;
import com.pomiecho.tasky.interfaces.CardClickListener;
import com.pomiecho.tasky.interfaces.Communicator;
import com.pomiecho.tasky.objects.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InProgressFragment extends Fragment {

    private SQLiteHandler db;
    private InProgressAdapter adapter;
    private List<Task> taskList;

    public Communicator communicator;
    public CardClickListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InProgressModel inProgressModel = new ViewModelProvider(this).get(InProgressModel.class);
        View root = inflater.inflate(R.layout.fragment_in_progress, container, false);
        db = new SQLiteHandler(getActivity());
        RecyclerView recyclerView = root.findViewById(R.id.recycler_in_progress);
        taskList = new ArrayList<>();

        adapter = new InProgressAdapter(getActivity(), taskList, new CardClickListener() {
            @Override
            public void toDoClick(View v, int position) {
                communicator.setTaskToDo(taskList.get(position));
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
            public void inProgressClick(View v, int position) { }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareTasks();

        inProgressModel.getText().observe(getViewLifecycleOwner(), s -> { });
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
        taskList.addAll(db.getTasks(2));
        updateRecyclerView();
    }

    public void addInProgressTask(Task task) {
        task.setState(2);
        db.updateTask(task);
        taskList.clear();
        prepareTasks();
    }
}