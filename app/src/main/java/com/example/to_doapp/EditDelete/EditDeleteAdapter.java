package com.example.to_doapp.EditDelete;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.Model.AllTaskClass;
import com.example.to_doapp.R;

import java.util.ArrayList;

public class EditDeleteAdapter extends RecyclerView.Adapter<EditDeleteViewHolder> {
    private ArrayList<AllTaskClass> allTaskClasses;

    public interface EDTaskIsClickedInterface {
        void edTaskIsClicked(ArrayList<AllTaskClass> allTaskClasses1);
    }

    private EDTaskIsClickedInterface edTaskIsClickedInterface;

    public EditDeleteAdapter(ArrayList<AllTaskClass> allTaskClasses, EDTaskIsClickedInterface edTaskIsClickedInterface) {
        this.allTaskClasses = allTaskClasses;
        this.edTaskIsClickedInterface = edTaskIsClickedInterface;
    }

    @NonNull
    @Override
    public EditDeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.edit_delete_view_holder, parent, false);

        return new EditDeleteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EditDeleteViewHolder holder, final int position) {
        holder.getTxtEDTask().setText(allTaskClasses.get(position).getAddedTask());
        if(allTaskClasses.get(position).isChecked() == 1) {
            holder.getCheckBox().setChecked(true);
        }else  holder.getCheckBox().setChecked(false);

        holder.getTxtEDTask().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allTaskClasses.get(holder.getAdapterPosition()).isChecked() == 1) {
                    allTaskClasses.get(holder.getAdapterPosition()).setChecked(0);
                    holder.getCheckBox().setChecked(false);
                } else {
                    allTaskClasses.get(holder.getAdapterPosition()).setChecked(1);
                    holder.getCheckBox().setChecked(true);
                }
                edTaskIsClickedInterface.edTaskIsClicked(allTaskClasses);
            }
        });
        holder.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allTaskClasses.get(holder.getAdapterPosition()).isChecked() == 1) {
                    allTaskClasses.get(holder.getAdapterPosition()).setChecked(0);
                    holder.getCheckBox().setChecked(false);
                } else {
                    allTaskClasses.get(holder.getAdapterPosition()).setChecked(1);
                    holder.getCheckBox().setChecked(true);
                }
                edTaskIsClickedInterface.edTaskIsClicked(allTaskClasses);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allTaskClasses.size();
    }
}
