package com.example.qaunewsalerts;

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

import com.example.qaunewsalerts.activities.AddNews;
import com.example.qaunewsalerts.activities.PublishNews;
import com.example.qaunewsalerts.activities.Signup;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.datamodals.UserInformation;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRequestDetail extends AppCompatActivity {
    private TextView deheading;
    private TextView dedesc;
    private ImageView deimage;
    private TextView NewsRequestUserID,NewsRequestUserDepartment,newsCategory;
    NewsRequestDetail newsRequestDetail;
    private List<NewsRequest> newsRequestList;
    private String newsTitle, newsDes, newsImage,userid,userdepart,newscategory;
    Button approve,discard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_request_detail);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            newsTitle = extras.getString("newsTitle");
            newsDes = extras.getString("newsDes");
            newsImage = extras.getString("newsImage");
            userid=extras.getString("UserID");
            userdepart=extras.getString("UserDepartment");
            newscategory=extras.getString("newsCategory");
         //   Log.d("image", newsImage);
        }

        deheading=findViewById(R.id.detail_request_heading);
        deheading.setText(newsTitle);

        deimage=findViewById(R.id.detail_request_image);
        Picasso.get().load(newsImage).into(deimage);

        dedesc=findViewById(R.id.detail_request_desc);
        dedesc.setText(newsDes);

        NewsRequestUserID=findViewById(R.id.Detail_newsrequest_UserId);
        NewsRequestUserID.setText(userid);

        NewsRequestUserDepartment=findViewById(R.id.Detail_newsrequest_UserDepartment);
        NewsRequestUserDepartment.setText(userdepart);

        newsCategory=findViewById(R.id.all_news_request_category);
        newsCategory.setText(newscategory);

        approve=findViewById(R.id.approve_news);
        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewsRequestDetail.this, PublishNews.class);
                intent.putExtra("newsTitle",newsTitle );
                intent.putExtra("newsDes",newsDes);
                intent.putExtra("newsImage",newsImage);
                intent.putExtra("newsCategory",newscategory);
                startActivity(intent);
            }
        });

        discard=findViewById(R.id.discard);
        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Requests").orderByChild("news_title").equalTo(newsTitle);

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                            Toast.makeText(newsRequestDetail, "News Request Successfully Deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(newsRequestDetail, "onCancelled", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

}