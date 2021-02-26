package com.example.qaunewsalerts.ui.useresettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserSettingsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public UserSettingsViewModel() {
        mText = new MutableLiveData<>();

}

    public LiveData<String> getText() {
        return mText;
    }
}
