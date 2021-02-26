package com.example.qaunewsalerts.ui.userprofile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.example.qaunewsalerts.activities.EditProfile;
import com.example.qaunewsalerts.R;
import com.google.firebase.database.DatabaseReference;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel profileViewModel;
    TextView name, Reid, eemail, password;
    TextView edit_email;
    TextView changePassword;
    TextView userDept;
    DatabaseReference databaseReference;
LinearLayout Change;

    public UserProfileFragment() {
        // Required empty public constructor

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel.class);

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
       // getUserInfo();

        name = root.findViewById(R.id.profilename);
        Reid = root.findViewById(R.id.profileid);
        eemail = root.findViewById(R.id.profileemail);
       userDept = root.findViewById(R.id.profiledepart);

        name.setText(Constants.getSharedString("name", "", getContext()));
        eemail.setText(Constants.getSharedString("email", "", getContext()));
        Reid.setText(Constants.getSharedString("userId", "", getContext()));
        userDept.setText(Constants.getSharedString("department", "", getContext()));

        edit_email = root.findViewById(R.id.eidt_email);
        Change=root.findViewById(R.id.change_password);
        changePassword=root.findViewById(R.id.changepassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Change.setVisibility(v.VISIBLE);

            }
        });
        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditProfile.class));
            }
        });


        return root;
    }



}
