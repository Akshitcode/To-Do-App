package com.example.to_doapp.EditDelete;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.R;
import com.example.to_doapp.R;

public class EditDeleteViewHolder extends RecyclerView.ViewHolder {

    private TextView txtEDTask;
    private CheckBox checkBox;


    public EditDeleteViewHolder(@NonNull View itemView) {
        super(itemView);

        txtEDTask = itemView.findViewById(R.id.txtEDTask);
        checkBox = itemView.findViewById(R.id.idCheckBox);
    }

    public TextView getTxtEDTask() {
        return txtEDTask;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
