package com.pomiecho.tasky.ui.inprogress;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InProgressModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InProgressModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}