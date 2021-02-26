package com.example.qaunewsalerts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Locale;

public class ChangeLanguage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel
                    ("MyNotification","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        //Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
        @Override
        protected void onStart() {
            super.onStart();
            if ((Constants.getSharedString("userId", " ", ChangeLanguage.this)).equals(" ")) {
//                if(Constants.getSharedString("Language","",ChangeLanguage.this).equals("English")){
//                    setLocale("en");
//
                startActivity(getIntent());
            } else {

                if((Constants.getSharedString("userId"," ",ChangeLanguage.this)).equals("000111222")){
                    MovetoAdmin();
                    setLocale("en");

                }
                else

                { MovetoRegisterUser();
                    setLocale("en");}

            }
        }
    private void showChangeLanguageDialog() {
        final String[] listItems={"اردو","English"};
        AlertDialog.Builder mBuilder=new AlertDialog.Builder(ChangeLanguage.this);
        mBuilder.setTitle("Choose Language \nزبان کا انتخاب کریں");
        mBuilder.setSingleChoiceItems(listItems ,-1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if( which==0)
                {
                    setLocale("ur");
                    gotohome();
                    recreate();
                    finish();
                  // Constants.setSharedString("Language","Urdu",ChangeLanguage.this);


                }
                else if(which==1)
                {
                    setLocale("en");
                    gotohome();
                    recreate();
                    finish();
                   // Constants.setSharedString("Language","English",ChangeLanguage.this);
                }

                //dismiss alert dialog when language selected
                dialog.dismiss();
            }
        });

        AlertDialog mDialog=mBuilder.create();
        mDialog.show();


    }
    private void setLocale(String lang) {
        Locale locale=new Locale(lang);
        locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("settings",MODE_MULTI_PROCESS).edit();
        editor.putString("My_lang",lang);
        editor.apply();

    }
    public void loadLocale(){
        SharedPreferences pre=getSharedPreferences("settings", Activity.MODE_MULTI_PROCESS);
        String language =pre.getString("My_lang","");
        setLocale(language);
    }

    public void gotohome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
    private void MovetoRegisterUser() {
        Intent intent=new Intent(ChangeLanguage.this,Registeruser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


        //startActivity(new Intent(getActivity(),Registeruser.class));

    }
    private void MovetoAdmin()
    {
        Intent intent=new Intent(ChangeLanguage.this,Admin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void btn_Skip(View view) {
        // if((Constants.getSharedString("userId"," ",ChangeLanguage.this)).equals(" ")){
        gotohome();
        //  }else{
        //   MovetoRegisterUser();
        //}
        finish();
    }

    public void Changelag_text(View view) {
        showChangeLanguageDialog();
    }
}