package com.pomiecho.tasky;

import android.annotation.SuppressLint;
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

    public static int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "tasks_db.db";


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

    public long insertTask(Task task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues tasks = new ContentValues();
        tasks.put(Task.TITLE, task.getTitle());
        tasks.put(Task.DESCRIPTION, task.getDescription());
        tasks.put(Task.STATE, 1);

        return db.insert(Task.TABLE_NAME, null, tasks);
    }

    public boolean updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Task.TITLE, getTask(task.getId()).getTitle());
        values.put(Task.DESCRIPTION, getTask(task.getId()).getDescription());
        values.put(Task.STATE, task.getState());

        db.update(Task.TABLE_NAME, values, Task.ID + " = ?",
                new String[]{String.valueOf(task.getId())});

        return true;
    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Task.TABLE_NAME, Task.ID + " = ?",
                new String[]{String.valueOf(task.getId())});
    }

    public Task getTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Task.TABLE_NAME,
                new String[]{Task.ID, Task.TITLE, Task.DESCRIPTION, Task.STATE},
                Task.ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        assert cursor != null;
        Task task = new Task(
                cursor.getInt(cursor.getColumnIndex(Task.ID)),
                cursor.getString(cursor.getColumnIndex(Task.TITLE)),
                cursor.getString(cursor.getColumnIndex(Task.DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(Task.STATE))
        );

        cursor.close();
        return task;
    }

    public List<Task> getTasks(int state) {
        List<Task> tasks = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query(Task.TABLE_NAME,
                new String[]{Task.ID, Task.TITLE, Task.DESCRIPTION},
                Task.STATE + "=?",
                new String[]{String.valueOf(state)}, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndex(Task.ID)));
                task.setTitle(cursor.getString(cursor.getColumnIndex(Task.TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(Task.DESCRIPTION)));
                task.setState(cursor.getInt(cursor.getColumnIndex(Task.STATE)));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        return tasks;
    }

    public int getTasksCount() {
        String countQuery = "SELECT * FROM " + Task.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void clearTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM " + Task.TABLE_NAME);
    }
}
