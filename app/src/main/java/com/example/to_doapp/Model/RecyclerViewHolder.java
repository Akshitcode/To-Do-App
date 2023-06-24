package com.example.to_doapp.Model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView txtTask;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        txtTask = itemView.findViewById(R.id.txtAddedTask);
    }

    public TextView getTxtTask() {
        return txtTask;
    }
}
