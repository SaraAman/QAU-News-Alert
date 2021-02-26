package com.example.qaunewsalerts.ui.usersaved;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserSavedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserSavedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Saved fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}