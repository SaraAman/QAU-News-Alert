package com.example.qaunewsalerts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;


import com.example.qaunewsalerts.fragments.AccountFragment;
import com.example.qaunewsalerts.fragments.ContactFragment;
import com.example.qaunewsalerts.fragments.Favorite;
import com.example.qaunewsalerts.fragments.GuestHomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
   private RecyclerView.Adapter mAdapter;
private GuestHomeFragment fhome;
private ContactFragment contact;
private AccountFragment login;
    private Favorite favorite;
    private Toolbar home_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout=(FrameLayout)findViewById(R.id.main_frame);
//     home_toolbar=(Toolbar) findViewById(R.id.home_toolbar);
//        setSupportActionBar(home_toolbar);
//        getSupportActionBar().setTitle(R.string.app_name);


        fhome=new GuestHomeFragment();
        contact=new ContactFragment();
        login=new AccountFragment();
        favorite =new Favorite();
        setFregment(fhome);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.Botton_nav);



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home_guest:
                        setFregment(fhome);
                       //bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        return true;
                    case R.id.contact:
                        setFregment(contact);
                        //bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        return true;
                    case R.id.login:
                       setFregment(login);
                       // bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        return true;
                    case R.id.fav:
                        setFregment(favorite);
                        // bottomNavigationView.setItemBackgroundResource(R.color.colorPrimary);
                        return true;
                    default:
                        return false;
                }
            }
        });

    }



    private void setFregment(Fragment fregment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fregment);
        fragmentTransaction.commit();

    }


}
