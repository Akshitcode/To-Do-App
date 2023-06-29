package com.example.to_doapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.to_doapp.EditDelete.EditDeleteAdapter;
import com.example.to_doapp.Model.AllTaskClass;
import com.example.to_doapp.Model.RecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_doapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.TaskIsClickedInterface, EditDeleteAdapter.EDTaskIsClickedInterface {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private int COUNT = 0;
    private Menu menu;
    private RecyclerView taskRecyclerView;
    private TaskDataBase taskDataBase = new TaskDataBase(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        ArrayList<AllTaskClass> allTaskClasses = taskDataBase.returnAllTaskObjectObjects();

        taskRecyclerView = findViewById(R.id.task_recycler_view);
        taskRecyclerView.setAdapter(new RecyclerAdapter(allTaskClasses, MainActivity.this));
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialogBox();
            }
        });
    }

    private void displayAlertDialogBox() {
        String alertTitle = getString(R.string.create_new_task);
        String setPositiveButtonTitle = getString(R.string.positiveTitle);
        String setNegativeButtonTitle = getString(R.string.negativeTitle);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        EditText edtTask = new EditText(this);
        edtTask.setHint(R.string.enter_new_task);
        edtTask.setGravity(1);
        edtTask.setInputType(InputType.TYPE_CLASS_TEXT);

        alertBuilder.setTitle(alertTitle);
        alertBuilder.setView(edtTask);

        alertBuilder.setPositiveButton(setPositiveButtonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AllTaskClass allTaskClass = new AllTaskClass(0, edtTask.getText().toString(), 0);

                taskDataBase.addTask(allTaskClass);

                RecyclerAdapter recyclerAdapter = (RecyclerAdapter) taskRecyclerView.getAdapter();
                recyclerAdapter.addTask(allTaskClass);
                Toast.makeText(MainActivity.this, "Task added successfully!!", Toast.LENGTH_SHORT).show();

            }
        });
        alertBuilder.setNegativeButton(setNegativeButtonTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertBuilder.setCancelable(false);
        alertBuilder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        if (COUNT > 0) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.delete_menu) {

            ArrayList<AllTaskClass> allTaskClasses = taskDataBase.returnAllTaskObjectObjects();
            for (AllTaskClass allTaskClass : allTaskClasses) {
                if (allTaskClass.isChecked() == 1) {
                    taskDataBase.deleteTaskFromDatabaseByID(allTaskClass.getId());
                }
            }
            removeMenuInflator();
//            TaskManager taskManager1 = new TaskManager(MainActivity.this);
            ArrayList<AllTaskClass> allTaskClasses1 = taskDataBase.returnAllTaskObjectObjects();
            binding.fab.setEnabled(true);
            Toast.makeText(this, "Task Deleted successfully!!", Toast.LENGTH_SHORT).show();
            taskRecyclerView.setAdapter(new RecyclerAdapter(allTaskClasses1, MainActivity.this));
            return true;

        } else if (id == R.id.edit_menu) {
            ArrayList<AllTaskClass> allTaskClasses1 = taskDataBase.returnAllTaskObjectObjects();
            for (AllTaskClass allTaskClass : allTaskClasses1) {
                if (allTaskClass.isChecked() == 1) {
                    String alertTitle = getString(R.string.edit);
                    String setPositiveButtonTitle = getString(R.string.save);
                    String setNegativeButtonTitle = getString(R.string.discard);

                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                    EditText edtTask = new EditText(this);
                    edtTask.setText(allTaskClass.getAddedTask());
                    edtTask.setGravity(1);
                    edtTask.setInputType(InputType.TYPE_CLASS_TEXT);

                    alertBuilder.setTitle(alertTitle);
                    alertBuilder.setView(edtTask);

                    alertBuilder.setPositiveButton(setPositiveButtonTitle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String old = allTaskClass.getAddedTask();
                            String newS = edtTask.getText().toString();
                            taskDataBase.modifyTask(allTaskClass.getId(), newS);
                            Toast.makeText(MainActivity.this, "Task updated successfully!!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                    alertBuilder.setNegativeButton(setNegativeButtonTitle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });

                    alertBuilder.setCancelable(false);
                    alertBuilder.create().show();
                }
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void edTaskIsClicked(ArrayList<AllTaskClass> allTaskClasses) {
        ArrayList<AllTaskClass> allTaskClasses1 = new ArrayList<>();
        int count = 0;
        for (AllTaskClass allTaskClass : allTaskClasses) {
            if (allTaskClass.isChecked() == 1) {
                count++;
                taskDataBase.modifyTaskToTrue(allTaskClass);
            }
        }
        if (count > 1) {
            menu.removeItem(R.id.edit_menu);
        } else if (count == 0) {
            removeMenuInflator();
            onBackPressed();
        } else {
            removeMenuInflator();
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
    }

    @Override
    public void taskIsClicked(ArrayList<AllTaskClass> allTaskClasses, AllTaskClass allTaskClass) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        taskDataBase.modifyTaskToTrue(allTaskClass);
        binding.fab.setEnabled(false);
        taskRecyclerView.setAdapter(new EditDeleteAdapter(allTaskClasses, this));
    }

    public void removeMenuInflator() {
        menu.removeItem(R.id.edit_menu);
        menu.removeItem(R.id.delete_menu);
    }

    @Override
    public void onBackPressed() {
        removeMenuInflator();
        ArrayList<AllTaskClass> allTaskClasses1 = taskDataBase.returnAllTaskObjectObjects();
        binding.fab.setEnabled(true);
        taskDataBase.modifyTaskToFalse(allTaskClasses1);
        TaskDataBase taskDataBase1 = new TaskDataBase(MainActivity.this);
        ArrayList<AllTaskClass> allTaskClasses2 = taskDataBase1.returnAllTaskObjectObjects();
        taskRecyclerView.setAdapter(new RecyclerAdapter(allTaskClasses2, MainActivity.this));

    }
}