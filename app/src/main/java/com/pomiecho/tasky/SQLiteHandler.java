package com.pomiecho.tasky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.pomiecho.tasky.objects.Task;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasks_db";


    public SQLiteHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Task.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Task.TABLE_NAME);
        onCreate(db);
    }

    public long insertTask(String title, String description){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues tasks = new ContentValues();
        tasks.put(Task.TITLE, title);
        tasks.put(Task.DESCRIPTION, description);
        tasks.put(Task.STATE, 1);

        long id = db.insert(Task.TABLE_NAME, null, tasks);
        db.close();
        return id;
    }

    public Task getTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Task.TABLE_NAME,
                new String[]{Task.ID, Task.TITLE, Task.DESCRIPTION},
                Task.ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Task task = new Task(
                cursor.getInt(cursor.getColumnIndex(Task.ID)),
                cursor.getString(cursor.getColumnIndex(Task.TITLE)),
                cursor.getString(cursor.getColumnIndex(Task.DESCRIPTION))
        );

        cursor.close();
        db.close();
        return task;
    }

    public List<Task> getTasks(int state) {
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Task.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(Task.ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(Task.TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(Task.DESCRIPTION)));

                tasks.add(task);
            } while (cursor.moveToNext());
        }

        db.close();
        return tasks;
    }

    public int getTasksCount() {
        String countQuery = "SELECT * FROM " + Task.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;

    }
}
