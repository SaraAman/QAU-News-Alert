package com.example.qaunewsalerts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qaunewsalerts.adaptor.AllNewsRequestAdaptor;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.example.qaunewsalerts.ui.adminnewsrequest.AdminNewsRequestFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Admin extends AppCompatActivity {
    private List<NewsRequest> newsRequestList;
    private RecyclerView newsRequestView;
    public int Counter;
    private AllNewsRequestAdaptor newsRequestadapter;
    DatabaseReference dReference;
    private AppBarConfiguration mAppBarConfiguration;
    TextView CountTxt;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = findViewById(R.id.admintoolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.admindrawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_admin);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_admin, R.id.nav_categories_admin, R.id.nav_newsRequest_admin)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.host_fragment_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //------------------------------------News Request counter



       //CountTxt = navigationView.findViewById(R.id.notification_badge);

        newsRequestView=findViewById(R.id.news_request_recycle);
        newsRequestList=new ArrayList<>();
        dReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        dReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        NewsRequest l=npsnapshot.getValue(NewsRequest.class);
                        newsRequestList.add(l);
                        Counter=newsRequestList.size();
                        String testString = String.valueOf(Counter);
                       CountTxt.setText(testString);
                        Log.d("request news Counter", String.valueOf(Counter));
                    }
                    // newsRequestadapter=new AllNewsRequestAdaptor(newsRequestList,getContext(),this);
                    //newsRequestView.setAdapter(newsRequestadapter);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//////////////////////////////////////////////////////////////////////////


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.admin, menu);

            final MenuItem menuItem_Notification = menu.findItem(R.id.action_notification_user);
            View actionViewNotification = MenuItemCompat.getActionView(menuItem_Notification);
           actionViewNotification.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   navController.navigate(R.id.nav_newsRequest_admin);
               }
           });

           CountTxt = (TextView) actionViewNotification.findViewById(R.id.notification_badge);
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.host_fragment_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout(MenuItem item) {
//        SessionManagement sessionManagement=new SessionManagement(Registeruser.this);
//        sessionManagement.removeSession();
        Constants.setSharedString("userId"," ",Admin.this);
        Constants.setSharedString("password"," ",Admin.this);
        Constants.setSharedString("email"," ",Admin.this);
        Constants.setSharedString("name"," ",Admin.this);
        Constants.userId=" ";
        Constants.password=" ";
        Constants.email="";
        Constants.name="";
        Toast.makeText(Admin.this, "LOGGED OUT", Toast.LENGTH_SHORT).show();
        MoveToGuestHome();

    }
    private void MoveToGuestHome() {
        Intent intent=new Intent(Admin.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
