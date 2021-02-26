package com.example.qaunewsalerts.ui.adminnewsrequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdminNewsRequestViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AdminNewsRequestViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is admin Saved");
    }

    public LiveData<String> getText() {
        return mText;
    }
}