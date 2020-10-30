package com.pomiecho.tasky.ui.inprogress;

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
import com.pomiecho.tasky.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class InProgressFragment extends Fragment {

    private SQLiteHandler db;
    private InProgressModel inProgressModel;
    private RecyclerView recyclerView;
    private InProgressAdapter adapter;
    private List<Task> taskList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        inProgressModel =
                new ViewModelProvider(this).get(InProgressModel.class);
        View root = inflater.inflate(R.layout.fragment_in_progress, container, false);
        db = new SQLiteHandler(getActivity());
        recyclerView = root.findViewById(R.id.recycler_in_progress);
        taskList = new ArrayList<>();
        adapter = new InProgressAdapter(getActivity(), taskList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        inProgressModel.getText().observe(getViewLifecycleOwner(), s -> { });
        return root;
    }
}