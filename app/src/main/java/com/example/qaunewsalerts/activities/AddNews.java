package com.example.qaunewsalerts.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qaunewsalerts.datamodals.AllDepartment_news;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.datamodals.UserInformation;
import com.example.qaunewsalerts.sharedpreferences.SharedPref;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;
import java.util.UUID;

public class AddNews extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText add_news_detail;
    EditText add_news_title_Request;
    String news_Description;
    String News_title;
    String addnewsspinner;
    UserInformation userInformation;
    DatabaseReference dReference, dref, departmentReference;
    FirebaseDatabase dbase;
    Button post_request;
    Button post_classnoti;
    Button post_departmentalnews;
    private StorageReference mStorageRef;
    private AllDepartment_news allDepartment_news;
    private NewsRequest newsRequest;
    ImageView requst_image_add;
    TextView upload_image_add;
    TextView take_image_add;
    Spinner request_news_categories;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int PICK_IMAGE_REQUEST = 1;
    Uri uri;
    String generatedFilePath;
    boolean selector=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);
        getSupportActionBar().setTitle("Request To Publish News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newsRequest = new NewsRequest();
        allDepartment_news = new AllDepartment_news();
        add_news_detail = findViewById(R.id.add_news_description);
        add_news_title_Request = findViewById(R.id.request_news_title);
        post_request = findViewById(R.id.post_request);
        post_classnoti = findViewById(R.id.classnoti);
        post_departmentalnews = findViewById(R.id.post_deptnoti);
        request_news_categories = findViewById(R.id.select_news_categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Select_categories, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        request_news_categories.setAdapter(adapter);
        request_news_categories.setOnItemSelectedListener(this);

        //-------------------------------------------select New Category---------------------------------

        request_news_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemSelected = String.valueOf(parent.getAdapter().getItem(position));
                Log.d( "onItemSelected: ",itemSelected);
                if(itemSelected.equalsIgnoreCase("Select News Category!")){
                   selector=true;
                }
                else{ addnewsspinner = request_news_categories.getSelectedItem().toString();}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //-------------------------------------------Take Image---------------------------------

        upload_image_add = findViewById(R.id.request_upload);
//        take_image_add = findViewById(R.id.takepic);
//
//        if (ContextCompat.checkSelfPermission(AddNews.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(AddNews.this, new String[]{Manifest.permission.CAMERA}, 100);
//        }
//
//        take_image_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 100);
//            }
//        });






        //-------------------------------------------Upload Image---------------------------------

        requst_image_add = findViewById(R.id.request_newsimage);
        upload_image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
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
            }
        });

        //-------------------------------------------fireBase Reference---------------------------------

        mStorageRef = FirebaseStorage.getInstance().getReference();
        dReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        departmentReference = FirebaseDatabase.getInstance().getReference().child("DepartmentNotification");
        dref = FirebaseDatabase.getInstance().getReference().child("UserInformation");


        /////--------------------------------------staff-----------------------------------

        if ((Constants.getSharedString("userId", " ", AddNews.this)).equals("staff1")
                || (Constants.getSharedString("userId", " ", AddNews.this)).equals("staff2")) {
            String userdepartment=  Constants.getSharedString("department","", AddNews.this);
            String useid=Constants.getSharedString("userId","", AddNews.this);
            post_departmentalnews.setVisibility(View.VISIBLE);
            post_departmentalnews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(selector==false){
//                        ((TextView)request_news_categories.getSelectedView()).setError("Please Select any Category");
//                    }
//else{
                    news_Description = add_news_detail.getText().toString();
                    News_title = add_news_title_Request.getText().toString();

                        allDepartment_news.setUserID(useid);
                        allDepartment_news.setUserDepartment(userdepartment);
                        allDepartment_news.setNewsDescription(news_Description);
                        // allDepartment_news.setnewsCategory(addnewsspinner);
                        allDepartment_news.setNews_title(News_title);
                        allDepartment_news.seturi(generatedFilePath);

                    mStorageRef.child("images/" + Constants.getSharedString("userId", "", AddNews.this));
                    departmentReference.child(String.valueOf(UUID.randomUUID())).
                            setValue(allDepartment_news)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(AddNews.this, "Successfully Added News", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNews.this, "Error in posting request", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            //}
            });
        }
        //----------------------------------faculty------------------------------

        else if ((Constants.getSharedString("userId", " ", AddNews.this)).equals("faculty") ||
                (Constants.getSharedString("userId", " ", AddNews.this)).equals("faculty1")) {
            String userdepartment=  Constants.getSharedString("department", "", AddNews.this);
            String useid=Constants.getSharedString("userId","", AddNews.this);
            post_classnoti.setVisibility(View.VISIBLE);
            post_classnoti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(selector==false){
//                        ((TextView)request_news_categories.getSelectedView()).setError("Please Select any Category");
//                    }
//            else{
                    news_Description = add_news_detail.getText().toString();
                    News_title = add_news_title_Request.getText().toString();


                        if(!TextUtils.isEmpty(generatedFilePath))
                            allDepartment_news.setUserID(useid);
                        allDepartment_news.setUserDepartment(userdepartment);
                        allDepartment_news.setNewsDescription(news_Description);
                        allDepartment_news.setnewsCategory(addnewsspinner);
                        allDepartment_news.setNews_title(News_title);
                        allDepartment_news.seturi(generatedFilePath);


                    mStorageRef.child("images/" + Constants.getSharedString("userId", "", AddNews.this));
                    departmentReference.child(String.valueOf(UUID.randomUUID())).setValue(allDepartment_news)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(AddNews.this, "Successfully Added News", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNews.this, "Error in posting request", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            //}
            });
        }
        //-----------------------------------------------User--------------------------------

        else {
            String userdepartment=  Constants.getSharedString("department", "", AddNews.this);
            String useid=Constants.getSharedString("userId","", AddNews.this);
            post_request.setVisibility(View.VISIBLE);
            request_news_categories.setVisibility(View.VISIBLE);
            post_request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    if(selector==true){
//                        ((TextView)request_news_categories.getSelectedView()).setError("Please Select any Category");
//
//                    }
//                    else{
                    news_Description = add_news_detail.getText().toString();
                    News_title = add_news_title_Request.getText().toString();

                    newsRequest.setUserID(useid);
                    newsRequest.setUserDepartment(userdepartment);
                    newsRequest.setNewsDescription(news_Description);
                    newsRequest.setnewsCategory(addnewsspinner);
                    newsRequest.setNews_title(News_title);
                    newsRequest.seturi(generatedFilePath);

                    mStorageRef.child("images/" + Constants.getSharedString("userId", "", AddNews.this));
                    dReference.child(String.valueOf(UUID.randomUUID()))
                            .setValue(newsRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(AddNews.this, "Successfully Added News", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNews.this, "Error in posting request", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
              //  }
            });
        }
    }



    private void uploadfile(Uri uri) {

        if (uri != null) {

            StorageReference ref
                    = FirebaseStorage.getInstance().getReference()
                    .child("images/" + UUID.randomUUID().toString());

            final ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Task<Uri> downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    downloadUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            generatedFilePath = uri.toString();
                          //  String useid=Constants.getSharedString("userId","", AddNews.this);
//                            if ((Constants.getSharedString("userId", " ", AddNews.this)).equals("faculty") ||
//                                    (Constants.getSharedString("userId", " ", AddNews.this)).equals("faculty1")||
//                                    (Constants.getSharedString("userId", " ", AddNews.this)).equals("staff1")
//                                    || (Constants.getSharedString("userId", " ", AddNews.this)).equals("staff2")){
//                                allDepartment_news.seturi(generatedFilePath);
//                            }
//                            else{
//                            newsRequest.seturi(generatedFilePath);
//                            }

                            // Toast.makeText(getApplicationContext(),generatedFilePath,Toast.LENGTH_LONG ).show();
                        }
                    });

                    Toast.makeText(AddNews.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddNews.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_LONG).show();
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            uri = data.getData();
            requst_image_add.setImageBitmap(bitmap);
            uploadfile(uri);
        } else if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            uri = data.getData();
            requst_image_add.setImageURI(data.getData());

            uploadfile(uri);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}