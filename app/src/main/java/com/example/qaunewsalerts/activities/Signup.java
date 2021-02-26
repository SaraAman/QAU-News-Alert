package com.example.qaunewsalerts.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.Registeruser;
import com.example.qaunewsalerts.datamodals.UserInformation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText myfullname, myregid, myemail, mypassword, myconfirmpass;
    Spinner spinner;
    ProgressBar signuprogress;
    FirebaseDatabase database;
    DatabaseReference databaseReference, dataref;
    private UserInformation userInformation;
    String match;
    String regId;
    String password;
    String confirmpassword;
    String email;
    String signupspinner;
    //boolean selector=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Signup Form");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.department_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Select_Department, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        myfullname = findViewById(R.id.fullname);
        myregid = findViewById(R.id.regid);
        myemail = findViewById(R.id.sigemail);
        mypassword = findViewById(R.id.signpass);
        myconfirmpass = findViewById(R.id.confirmpass);
        signuprogress = findViewById(R.id.progressBar);
        userInformation = new UserInformation();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("UserInformation");
        dataref = database.getReference().child("Register-id");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Department!")){
                   // selector=false;
                }
                else{  signupspinner = spinner.getSelectedItem().toString();}

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void btn_register(View view) {
        regId = myregid.getText().toString();
        password = mypassword.getText().toString();
        confirmpassword = myconfirmpass.getText().toString();

        email = myemail.getText().toString();
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            if (confirmpassword.equals(password)) {

                try {
                    dataref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                match = child.getKey();

                                if (match.equals(regId)) {
                                    signuprogress.setVisibility(View.VISIBLE);
                                    userInformation.setId(regId);
                                    userInformation.setPassword(password);
                                    userInformation.setFullname(myfullname.getText().toString());
                                    userInformation.setEmail(email);
                                    userInformation.setDepartment(signupspinner);
                                    databaseReference.child(userInformation.getId()).setValue(userInformation).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            Constants.setSharedString("userId", regId, getApplicationContext());
                                            Constants.setSharedString("password", password, getApplicationContext());
                                            Constants.userId = regId;
                                            Constants.password = password;
                                            Toast.makeText(Signup.this, "Register SuccessFull", Toast.LENGTH_LONG).show();
                                            getUserInfo();
                                            MovetoRegisterUser();
                                        }
                                    });
                                } else {
                                    signuprogress.setVisibility(View.VISIBLE);
                                    Toast.makeText(Signup.this, "Please Enter Valid Registration ID", Toast.LENGTH_LONG).show();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                } catch (Exception ex) {
                    signuprogress.setVisibility(View.VISIBLE);
                    Toast.makeText(Signup.this, "Please Enter Valid Registration ID", Toast.LENGTH_LONG).show();
                    ex.printStackTrace();
                }
            } else {
                signuprogress.setVisibility(View.VISIBLE);
                myconfirmpass.setError("password Not match");
            }
        } else {
            signuprogress.setVisibility(View.VISIBLE);
            myemail.setError("Invalid Email Address");
        }

    }

    private void MovetoRegisterUser() {
        Intent intent = new Intent(Signup.this, Registeruser.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


        //startActivity(new Intent(getActivity(),Registeruser.class));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                            if ((userInformation.id).equals(Constants.getSharedString("userId", "", Signup.this))) {
                                //Toast.makeText(getContext(), userInformation.getFullname(), Toast.LENGTH_SHORT).show();
                                Constants.setSharedString("name", userInformation.fullname, Signup.this);
                                Constants.setSharedString("email", userInformation.email, Signup.this);
                                Constants.setSharedString("department", userInformation.department, Signup.this);

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
}