package com.example.qaunewsalerts.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qaunewsalerts.Admin;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.example.qaunewsalerts.Faculty;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.Registeruser;
import com.example.qaunewsalerts.activities.Signup;
import com.example.qaunewsalerts.Staff;
import com.example.qaunewsalerts.datamodals.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

import static com.example.qaunewsalerts.MainActivity.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    TextView Signup_text;
    Button loginBtn;
    EditText registrationID, Password;
    ProgressBar loginprogress;
    UserInformation userInformation = new UserInformation();
    private DatabaseReference reff, adminreff;
    String pasword, regid;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_account, container, false);

        Signup_text = rootview.findViewById(R.id.signup);
        registrationID = rootview.findViewById(R.id.useregid);
        Password = rootview.findViewById(R.id.userpass);
        loginprogress = rootview.findViewById(R.id.login_progressBar);
        reff = FirebaseDatabase.getInstance().getReference().child("UserInformation");
        adminreff = FirebaseDatabase.getInstance().getReference().child("Admin");
        loginBtn = rootview.findViewById(R.id.loginbutton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regid = registrationID.getText().toString();
                pasword = Password.getText().toString();
                if (TextUtils.isEmpty(regid)) {
                    loginprogress.setVisibility(View.INVISIBLE);
                    registrationID.setError("Reg-ID Required");
                    return;
                }
                else
                    //-------------------------Admin------------------------
                if (regid.equals("000111222")) {
                    adminreff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String adminPass = snapshot.child("password").getValue(String.class);
                            if (pasword.equals(adminPass)) {
                                loginprogress.setVisibility(View.VISIBLE);
                                Constants.setSharedString("userId", regid, getContext());
                                Constants.setSharedString("password", pasword, getContext());
                                Constants.userId = regid;
                                Constants.password = pasword;

                                Toast.makeText(getContext(), "Admin SuccessFull login", Toast.LENGTH_LONG).show();
                                setLocale("en");
                                MovetoAdminActivity();

                            } else {
                                Toast.makeText(getContext(), "Enter Correct Password !", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {


                        }
                    });
                }

                //---------------------------------------faculty------------------------------

                else if (regid.equals("faculty")||regid.equals("faculty1")) {

                    try {
                        reff.child(regid).addValueEventListener(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                UserInformation userinformation = snapshot.getValue(UserInformation.class);
                                if (regid.equals((snapshot.child("id").getValue(String.class))))
                                {

                                    if ((pasword.equals(snapshot.child("password").getValue(String.class))))
                                    {
                                        loginprogress.setVisibility(View.VISIBLE);
                                        Constants.setSharedString("userId", regid, getContext());
                                        Constants.setSharedString("password", pasword, getContext());
                                        Constants.userId = regid;
                                        Constants.password = pasword;
//                            SessionManagement sessionManagement=new SessionManagement(getActivity());
//                            sessionManagement.saveSession(userInformation);

                                        Toast.makeText(getContext(), "Welcome as a Faculty", Toast.LENGTH_LONG).show();
                                        setLocale("en");
                                        MovetofacultyActivity();
                                        getUserInfo();
                                        //startActivity(new Intent(getActivity(),Registeruser.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Enter Correct Password !", Toast.LENGTH_LONG).show();
                                    }
                                } else
                                {
                                    Toast.makeText(getContext(), "Enter Correct Id !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error)
                            {
                                Toast.makeText(getContext(), "Sorry!Account Required ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    catch (Exception e) {
                        loginprogress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Registration ID Required", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                //----------------------------------staff---------------------------------

                else if (regid.equals("staff1")||regid.equals("satff2")) {
                    try {
                        reff.child(regid).addValueEventListener(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                UserInformation userinformation = snapshot.getValue(UserInformation.class);
                                if (regid.equals((snapshot.child("id").getValue(String.class))))
                                {

                                    if ((pasword.equals(snapshot.child("password").getValue(String.class))))
                                    {
                                        loginprogress.setVisibility(View.VISIBLE);
                                        Constants.setSharedString("userId", regid, getContext());
                                        Constants.setSharedString("password", pasword, getContext());
                                        Constants.userId = regid;
                                        Constants.password = pasword;
//                            SessionManagement sessionManagement=new SessionManagement(getActivity());
//                            sessionManagement.saveSession(userInformation);

                                        Toast.makeText(getContext(), "Welcome as a Staff", Toast.LENGTH_LONG).show();
                                        setLocale("en");
                                        MovetostaffActivity();
                                        getUserInfo();
                                        //startActivity(new Intent(getActivity(),Registeruser.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Enter Correct Password !", Toast.LENGTH_LONG).show();
                                    }
                                } else
                                {
                                    Toast.makeText(getContext(), "Enter Correct Id !", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error)
                            {
                                Toast.makeText(getContext(), "Sorry!Account Required ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    catch (Exception e) {
                        loginprogress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Registration ID Required", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

//----------------------------------------- students----------------------------
               else {
                    try {
                        reff.child(regid).addValueEventListener(new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot)
                            {
                                UserInformation userinformation = snapshot.getValue(UserInformation.class);
                                if (regid.equals((snapshot.child("id").getValue(String.class))))
                                {

                                    if ((pasword.equals(snapshot.child("password").getValue(String.class))))
                                    {
                                        loginprogress.setVisibility(View.VISIBLE);
                                        Constants.setSharedString("userId", regid, getContext());
                                        Constants.setSharedString("password", pasword, getContext());
                                        Constants.userId = regid;
                                        Constants.password = pasword;
//                            SessionManagement sessionManagement=new SessionManagement(getActivity());
//                            sessionManagement.saveSession(userInformation);

                                        Toast.makeText(getContext(), "SuccessFull login", Toast.LENGTH_LONG).show();
                                        setLocale("en");
                                        MovetoRegisterUser();
                                        getUserInfo();
                                        //startActivity(new Intent(getActivity(),Registeruser.class));
                                    }
                                    else
                                        {
                                        Toast.makeText(getContext(), "Enter Correct Password !", Toast.LENGTH_LONG).show();
                                        }
                                } else
                                    {
                                    Toast.makeText(getContext(), "Enter Correct Id !", Toast.LENGTH_LONG).show();
                                    }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error)
                            {
                                Toast.makeText(getContext(), "Sorry!Account Required ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    catch (Exception e) {
                        loginprogress.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Registration ID Required", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

            }
        });
        Signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Signup.class));
            }
        });
        return rootview;
    }

    private void MovetoRegisterUser() {
        Intent intent = new Intent(getActivity(), Registeruser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


        //startActivity(new Intent(getActivity(),Registeruser.class));

    }

    private void MovetoAdminActivity() {
        Intent intent = new Intent(getActivity(), Admin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void MovetostaffActivity() {
        Intent intent = new Intent(getActivity(), Staff.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void MovetofacultyActivity() {
        Intent intent = new Intent(getActivity(), Faculty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void getUserInfo() {
        DatabaseReference profile_data = FirebaseDatabase.getInstance().getReference("UserInformation");
        profile_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot != null) {
                        for (DataSnapshot listDataSnapshot : snapshot.getChildren()) {
                            UserInformation userInformation = listDataSnapshot.getValue(UserInformation.class);
                            if ((userInformation.id).equals(Constants.getSharedString("userId", "", getContext()))) {
                                //Toast.makeText(getContext(), userInformation.getFullname(), Toast.LENGTH_SHORT).show();
                                Constants.setSharedString("name", userInformation.fullname, getContext());
                                Constants.setSharedString("email", userInformation.email, getContext());
                                Constants.setSharedString("department", userInformation.department, getContext());

                                break;
                            }
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getContext().getResources().updateConfiguration(configuration, getContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = this.getActivity().getSharedPreferences("settings", MODE_MULTI_PROCESS).edit();
        editor.putString("My_lang", lang);
        editor.apply();

    }

    public void loadLocale() {
        SharedPreferences pre = this.getActivity().getSharedPreferences("settings", Activity.MODE_MULTI_PROCESS);
        String language = pre.getString("My_lang", "");
        setLocale(language);
    }
}


