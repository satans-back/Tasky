package com.pomiecho.tasky.interfaces;

import android.view.View;

public interface CardClickListener {
    void toDoClick(View v, int position);
    void inProgressClick(View v, int position);
    void doneClick(View v, int position);
    void deleteClick(View v, int position);
}
