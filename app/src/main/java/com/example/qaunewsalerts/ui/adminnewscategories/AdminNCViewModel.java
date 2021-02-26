package com.example.qaunewsalerts.ui.adminnewscategories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminNCViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdminNCViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}