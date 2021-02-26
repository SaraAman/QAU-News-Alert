package com.example.qaunewsalerts.ui.useresettings;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qaunewsalerts.activities.MyNotification;
import com.example.qaunewsalerts.activities.MyPush;
import com.example.qaunewsalerts.R;

import java.util.Locale;

public class UserSettingsFragment extends Fragment {

    TextView manageMyPush;
    TextView setnotification;
    private NavController navController;
    private UserSettingsViewModel mViewModel;

    public static UserSettingsFragment newInstance() {
        return new UserSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_settings, container, false);


        navController=Navigation.findNavController(getActivity(),R.id.nav_host_user);
        manageMyPush = rootview.findViewById(R.id.my_push);
        manageMyPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPush.class));
            }
        });

        setnotification = rootview.findViewById(R.id.my_notifications);
        setnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyNotification.class));
            }
        });
        return rootview;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserSettingsViewModel.class);
        // TODO: Use the ViewModel
    }


    private void showChangeLanguageDialog() {
        final String[] listItems = {"اردو", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle("Choose Language");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    setLocale("ur");
                    goToHome();
                    getActivity().recreate();


                } else if (which == 1) {
                    setLocale("en");
                    goToHome();
                    getActivity().recreate();


                }

                //dismiss alert dialog when language selected
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();


    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(configuration, getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("settings", Context.MODE_MULTI_PROCESS).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences pre = getActivity().getSharedPreferences("settings", Activity.MODE_MULTI_PROCESS);
        String language = pre.getString("My_lang", "");
        setLocale(language);
    }

    public void goToHome() {
        navController.navigate(R.id.nav_setting_user);
    }


}




