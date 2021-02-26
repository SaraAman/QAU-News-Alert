package com.example.qaunewsalerts.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class DetailNews extends AppCompatActivity {

    private TextView deheading;
    private TextView dedesc;
    private ImageView deimage;

    private String newsTitle, newsDes, newsImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            newsTitle = extras.getString("newsTitle");
            newsDes = extras.getString("newsDes");
            newsImage = extras.getString("newsImage");
            Log.d("image", newsImage);
        }

        setContentView(R.layout.activity_detail_news);

        getSupportActionBar().setTitle("News Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        deheading=findViewById(R.id.detailheading);
        deheading.setText(newsTitle);

        deimage=findViewById(R.id.detailimage);
        Picasso.get().load(newsImage).into(deimage);

        dedesc=findViewById(R.id.detaildesc);
        dedesc.setText(newsDes);


    }

}
