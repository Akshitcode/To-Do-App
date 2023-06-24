package com.example.to_doapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.to_doapp.Model.AllTaskClass;

import java.util.ArrayList;

public class TaskDataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "toDoDataBase";
    private static final int DATABASE_VERSION = 1;
    private static final String TO_DO_TASK_TABLE = "toDoTable";
    private static final String ID_KEY = "id";
    private static final String TASK_KEY = "text";
    private static final String IS_CHECKED_KEY = "isChecked";


    public TaskDataBase(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createDatabaseSQL = "create table " + TO_DO_TASK_TABLE +
                "(" + ID_KEY + " integer primary key autoincrement" +
                ", " + TASK_KEY + " text" + ", " + IS_CHECKED_KEY + " integer" + ")";
        db.execSQL(createDatabaseSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if EXISTS " + TO_DO_TASK_TABLE);
        onCreate(db);
    }

    public void addTask(AllTaskClass allTaskClass) {

        SQLiteDatabase database = getWritableDatabase();
        String addSQLCommand = "insert into " + TO_DO_TASK_TABLE +
                " values(null,'" + allTaskClass.getAddedTask() + "', '" + allTaskClass.isChecked() + "')";
        database.execSQL(addSQLCommand);
        database.close();
    }

    public void deleteTaskFromDatabaseByID(int id) {

        SQLiteDatabase database = getWritableDatabase();
        String deleteSQLCommand = "delete from " + TO_DO_TASK_TABLE +
                " where " + ID_KEY + "=" + id;
        database.execSQL(deleteSQLCommand);
        database.close();
    }

    public void modifyTask(int id, String newTask) {
        SQLiteDatabase database = getWritableDatabase();
        String modifySQLCommand = "update " + TO_DO_TASK_TABLE +
                " set " + TASK_KEY + "= '" + newTask + "' " + "where " + ID_KEY + " = " + id;
        database.execSQL(modifySQLCommand);
        database.close();
    }

    public void modifyTaskToTrue(AllTaskClass allTaskClass) {
        SQLiteDatabase database = getWritableDatabase();
        String modifySQLCommand = "update " + TO_DO_TASK_TABLE +
                " set " + IS_CHECKED_KEY + "= '" + 1 + "' " + "where " + ID_KEY + " = " + allTaskClass.getId();
        database.execSQL(modifySQLCommand);
        database.close();
    }

    public void modifyTaskToFalse(ArrayList<AllTaskClass> allTaskClasses) {
        for (AllTaskClass allTaskClass : allTaskClasses) {
            SQLiteDatabase database = getWritableDatabase();
            String modifySQLCommand = "update " + TO_DO_TASK_TABLE +
                    " set " + IS_CHECKED_KEY + "= '" + 0 + "' " + "where " + ID_KEY + " = " + allTaskClass.getId();
            database.execSQL(modifySQLCommand);
            database.close();
        }
    }

    public ArrayList<AllTaskClass> returnAllTaskObjectObjects() {
        SQLiteDatabase database = getWritableDatabase();
        String sqlQueryCommand = "Select * from " + TO_DO_TASK_TABLE;
        Cursor cursor = database.rawQuery(sqlQueryCommand, null);

        ArrayList<AllTaskClass> allTaskClasses = new ArrayList<>();
        while (cursor.moveToNext()) {

            AllTaskClass currentTask = new AllTaskClass(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), Integer.parseInt(cursor.getString(2)));
            allTaskClasses.add(currentTask);
        }
        database.close();
        return allTaskClasses;
    }


}
