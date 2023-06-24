package com.example.to_doapp.Model;

import java.io.Serializable;

public class AllTaskClass implements Serializable {

    private String addedTask;
    private int id;
    private int isChecked;

    public AllTaskClass(int id,String addedTask,int isChecked) {
        this.id = id;
        this.addedTask = addedTask;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public String getAddedTask() {
        return addedTask;
    }

    public void setAddedTask(String addedTask) {
        this.addedTask = addedTask;
    }

    public int isChecked() {
        return isChecked;
    }

    public void setChecked(int checked) {
        isChecked = checked;
    }

}
