package com.example.to_doapp.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<AllTaskClass> allTaskClasses;
    private AllTaskClass allTaskClass;

    public interface TaskIsClickedInterface {
        void taskIsClicked(ArrayList<AllTaskClass> allTaskClasses, AllTaskClass allTaskClass);
    }

    private TaskIsClickedInterface taskIsClickedInterface;

    public RecyclerAdapter(ArrayList<AllTaskClass> allTaskClasses, TaskIsClickedInterface taskIsClickedInterface) {
        this.allTaskClasses = allTaskClasses;
        this.taskIsClickedInterface = taskIsClickedInterface;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.to_do_view_holder, parent, false);

        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.getTxtTask().setText(allTaskClasses.get(position).getAddedTask());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                allTaskClasses.get(holder.getAdapterPosition()).setChecked(1);
                taskIsClickedInterface.taskIsClicked(allTaskClasses, allTaskClasses.get(holder.getAdapterPosition()));
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return allTaskClasses.size();
    }

    public void addTask(AllTaskClass allTaskClass) {
        allTaskClasses.add(allTaskClass);
        notifyItemInserted(allTaskClasses.size() - 1);
    }
}
