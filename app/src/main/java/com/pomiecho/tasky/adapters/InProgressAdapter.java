package com.pomiecho.tasky.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pomiecho.tasky.R;
import com.pomiecho.tasky.objects.Task;

import java.util.List;

public class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.MyViewHolder> {

    private Context mContext;
    private List<Task> taskList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        Button inProgressButton;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.task_title);
            description = view.findViewById(R.id.task_desc);
            inProgressButton = view.findViewById(R.id.card_in_progress_button);
            //inProgressButton.setEnabled(false);
        }
    }


    public InProgressAdapter(Context mContext, List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_task, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
