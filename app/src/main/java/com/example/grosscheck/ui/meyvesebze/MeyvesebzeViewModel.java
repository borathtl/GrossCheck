package com.example.grosscheck.ui.meyvesebze;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeyvesebzeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeyvesebzeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is meyvesebze fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}