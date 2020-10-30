package com.pomiecho.tasky.ui.done;

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
import com.pomiecho.tasky.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class DoneFragment extends Fragment {

    private SQLiteHandler db;
    private DoneModel doneModel;
    private RecyclerView recyclerView;
    private DoneAdapter adapter;
    private List<Task> taskList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        doneModel =
                new ViewModelProvider(this).get(DoneModel.class);
        View root = inflater.inflate(R.layout.fragment_done, container, false);

        db = new SQLiteHandler(getActivity());
        recyclerView = root.findViewById(R.id.recycler_done);
        taskList = new ArrayList<>();
        adapter = new DoneAdapter(getActivity(), taskList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        for(Task task : db.getTasks(3)) {
            taskList.add(task);
        }

        adapter.notifyDataSetChanged();

        doneModel.getText().observe(getViewLifecycleOwner(), s -> { });
        return root;
    }
}