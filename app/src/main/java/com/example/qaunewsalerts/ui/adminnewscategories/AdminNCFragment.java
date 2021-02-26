package com.example.qaunewsalerts.ui.adminnewscategories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.qaunewsalerts.R;

public class AdminNCFragment extends Fragment {

    private AdminNCViewModel adminNCViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adminNCViewModel =
                ViewModelProviders.of(this).get(AdminNCViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anc, container, false);

        return root;
    }
}
