package com.example.grosscheck.ui.sutkahvalti;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SutkahvaltiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SutkahvaltiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sutkahvalti fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}