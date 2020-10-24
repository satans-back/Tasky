package com.pomiecho.tasky.ui.todo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToDoModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ToDoModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ToDo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}