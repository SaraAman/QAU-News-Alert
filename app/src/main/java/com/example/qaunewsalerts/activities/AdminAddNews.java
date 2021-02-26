package com.example.qaunewsalerts.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.qaunewsalerts.datamodals.BackgroudTask;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AdminAddNews extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView publishimage;
    EditText title,description;
    Spinner category;
    TextView upload_image_add;
    TextView take_image_add;

    Uri uri;
    String generatedFilePath;
    private String newsTitle, newsDes, newsImage,newscategory;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_news);


        category=findViewById(R.id.admin_add_uni_select_news_categories);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Select_categories,android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);



        title=findViewById(R.id.admin_add_uni_title);
        description=findViewById(R.id.admin_add_uni_news_description);
        publishimage=findViewById(R.id.admin_add_uni_newsimage);

        /////////////////////////////////////////////
        upload_image_add=findViewById(R.id.admin_add_uni_upload);
        upload_image_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[] Permission={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(Permission,PERMISSION_CODE);
                    }
                    else
                    {
                        pickImageFromGallery();
                    }
                }
                else
                {
                    pickImageFromGallery();
                }
            }
        });
//////////////////////////////////////////
//        take_image_add = findViewById(R.id.admin_add_uni_takepic);
//
//        if (ContextCompat.checkSelfPermission(AdminAddNews.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(AdminAddNews.this, new String[]{Manifest.permission.CAMERA}, 100);
//        }
//
//        take_image_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, 100);
//            }
//        });

        //publishbnewsbtn=findViewById(R.id.publish_btn);


    }


    public void adminaddnews(View view) {
        newsTitle= title.getText().toString();
        newsDes= description.getText().toString();
        String method="submit";
        BackgroudTask backgroudTask=new BackgroudTask(this);
        backgroudTask.execute(method,newsTitle,newsDes,generatedFilePath);

        Intent intent = new Intent(AdminAddNews.this, Admin.class);
        startActivity(intent);

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
                            if(downloadUri.isSuccessful()){
                                String generatedFilePath = downloadUri.getResult().toString();
                                System.out.println("## Stored path is "+generatedFilePath);
                            }


                        }
                    });

                    Toast.makeText(AdminAddNews.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminAddNews.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            publishimage.setImageBitmap(bitmap);
            uploadfile(uri);
        } else if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            uri = data.getData();
            publishimage.setImageURI(data.getData());

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
