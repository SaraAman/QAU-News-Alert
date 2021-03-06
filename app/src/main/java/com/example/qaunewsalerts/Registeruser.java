package com.example.qaunewsalerts;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Registeruser extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);

        Toolbar toolbar = findViewById(R.id.registertoolbar);
        setSupportActionBar(toolbar);
//
        DrawerLayout drawer = findViewById(R.id.registerdrawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_registerview);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_user, R.id.nav_categories_user, R.id.nav_saved_user,R.id.nav_profile_user,R.id.nav_setting_user)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_user);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Constants.getSharedString("newsCategories", " ", Registeruser.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registeruser, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_user);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void logout(MenuItem item) {
//        SessionManagement sessionManagement=new SessionManagement(Registeruser.this);
//        sessionManagement.removeSession();
        Constants.setSharedString("userId"," ",Registeruser.this);
        Constants.setSharedString("password"," ",Registeruser.this);
        Constants.setSharedString("email"," ",Registeruser.this);
        Constants.setSharedString("name"," ",Registeruser.this);
        Constants.userId=" ";
        Constants.password=" ";
        Constants.email="";
        Constants.name="";
        Toast.makeText(Registeruser.this, "LOGGED OUT", Toast.LENGTH_SHORT).show();
        MoveToGuestHome();

    }

    private void MoveToGuestHome() {
       Intent intent=new Intent(Registeruser.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
       startActivity(intent);

    }
}
