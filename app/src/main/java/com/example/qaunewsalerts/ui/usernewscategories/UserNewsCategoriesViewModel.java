package com.example.qaunewsalerts.ui.usernewscategories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserNewsCategoriesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserNewsCategoriesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Categories fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}