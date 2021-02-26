package com.example.qaunewsalerts.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qaunewsalerts.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    ListView listView;
private static final int REQUEST_CALL=1;
    //public static final int REQUEST_CALL = 1;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_contact, container, false);


       rootview.findViewById(R.id.clickable_website).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               link("http://qau.edu.pk/");
           }
       });
        rootview.findViewById(R.id.clickable_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Number = "+9251 9064 0000";
                if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.
                        CALL_PHONE) != PackageManager.PERMISSION_GRANTED)) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                }
                else {
                    String Dial = "tel:" + Number;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(Dial)));
                }
            }
        });


        return rootview;
    }


    public void link(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
}

    }

