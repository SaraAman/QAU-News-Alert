package com.example.qaunewsalerts.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.sharedpreferences.Constants;

import java.sql.Array;

import static com.example.qaunewsalerts.sharedpreferences.Constants.newsCategories;

public class MyPush extends AppCompatActivity {
    TextView departments;
    CheckBox admission,sports, education,art,transport;
    int size =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_push);
        getSupportActionBar().setTitle("Manage My Push");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        admission=findViewById(R.id.addmission_checkbox);
        sports=findViewById(R.id.sports_checkbox);
        education=findViewById(R.id.education_checkbox);
        art=findViewById(R.id.art_checkbox);
        transport=findViewById(R.id.transport_checkbox);

        departments=findViewById(R.id.manage_departments);
        departments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPush.this, Departments.class));
            }
        });

        /////////////////
//        String array[]= new String[size];
//         Constants.setSharedString("newsCategories",array[size],getBaseContext() );

    }

//    public void addmission_checkbox(View view) {
//        //code to check if this checkbox is checked!
//       // CheckBox checkBox = (CheckBox)view;
//            size++;
//
//
//    }
//
//    public void education_checkbox(View view) {
//       // CheckBox checkBox = (CheckBox)view;
//            size++;
//
//
//
//    }
//
//    public void sports_checkbox(View view) {
//        //CheckBox checkBox = (CheckBox)view;
//            size++;
//
//
//    }
//
//    public void art_checkbox(View view) {
//       // CheckBox checkBox = (CheckBox)view;
//            size++;
//
//    }
//
//    public void transport_checkbox(View view) {
//        //CheckBox checkBox = (CheckBox)view;
//            size++;
//
//    }
}
