package com.example.qaunewsalerts.ui.usernewscategories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.qaunewsalerts.R;

public class UserNewsCategoriesFragment extends Fragment {

    private UserNewsCategoriesViewModel newsCategoriesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsCategoriesViewModel =
                ViewModelProviders.of(this).get(UserNewsCategoriesViewModel.class);

        View root = inflater.inflate(R.layout.fragment_newscategories, container, false);

        return root;
    }
}
