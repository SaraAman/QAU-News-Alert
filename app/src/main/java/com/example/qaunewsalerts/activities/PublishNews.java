package com.example.qaunewsalerts.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qaunewsalerts.Admin;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.Registeruser;
import com.example.qaunewsalerts.datamodals.BackgroudTask;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PublishNews extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
        ImageView publishimage;
        EditText title,description,category;
     Button upload_photo;
    Button publishbnewsbtn;
    private String newsTitle, newsDes, newsImage,newscategory;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_news);
        getSupportActionBar().setTitle("Publish News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            newsTitle = extras.getString("newsTitle");
            newsDes = extras.getString("newsDes");
            newsImage = extras.getString("newsImage");
            newscategory = extras.getString("newsCategory");
        }


        category = findViewById(R.id.select_dialog_listview);
        category.setText(newscategory);
//        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Select_categories,android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);


        title = findViewById(R.id.adminnews_title);
        title.setText(newsTitle);

        description = findViewById(R.id.adminnews_description);
        description.setText(newsDes);

        publishimage = findViewById(R.id.publishnewsimage);
        Picasso.get().load(newsImage).into(publishimage);


        //
        if (newsImage.equals(null)){
            upload_photo=findViewById(R.id.upload);
            upload_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                            String[] Permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            requestPermissions(Permission, PERMISSION_CODE);
                        } else {
                            pickImageFromGallery();
                        }
                    } else {
                        pickImageFromGallery();
                    }
                }
            });
    }
        //publishbnewsbtn=findViewById(R.id.publish_btn);


    }

    private void pickImageFromGallery()
    {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_CODE:
                {
                if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    pickImageFromGallery();
                }
                else
                    {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            publishimage.setImageURI(data.getData());
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text=parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void publishbnewsbtn(View view) {


        String method="submit";
        BackgroudTask backgroudTask=new BackgroudTask(this);
        backgroudTask.execute(method,newsTitle,newsDes,newsImage);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Requests").orderByChild("news_title").equalTo(newsTitle);


        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Toast.makeText(PublishNews.this, "News Request Successfully Deleted", Toast.LENGTH_SHORT).show();
                    finish();

                }
                Intent intent = new Intent(PublishNews.this, NewsRequest.class);
                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PublishNews.this, "onCancelled", Toast.LENGTH_SHORT).show();
            }
        });

        //finish();


    }
}
