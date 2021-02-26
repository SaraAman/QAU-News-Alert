package com.example.qaunewsalerts.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.Registeruser;
import com.example.qaunewsalerts.adaptor.AllNewsRequestAdaptor;
import com.example.qaunewsalerts.adaptor.AllnewsRecyclerAdaptopr;
import com.example.qaunewsalerts.adaptor.DepartmentNotificationAdaptor;
import com.example.qaunewsalerts.datamodals.AllDepartment_news;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.ui.adminnewsrequest.AdminNewsRequestViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Departmental_Notifications extends AppCompatActivity  implements DetailNewsInterface {
    int position = 0;
    private List<AllDepartment_news> newsdepartmentList;
    private RecyclerView newsdepartmentView;
    DatabaseReference dReference, dref;
    private DepartmentNotificationAdaptor newsdepartmentadapter;
 Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmental__notifications);
        home=findViewById(R.id.butn_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Departmental_Notifications.this, Registeruser.class));
            }
        });

        newsdepartmentView=findViewById(R.id.all_Department_recycle);
        newsdepartmentView.setHasFixedSize(true);
        newsdepartmentView.setLayoutManager(new LinearLayoutManager(Departmental_Notifications.this));
        newsdepartmentList=new ArrayList<>();
                dReference = FirebaseDatabase.getInstance().getReference().child("DepartmentNotification");
                dReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                                AllDepartment_news l=npsnapshot.getValue(AllDepartment_news.class);
                                newsdepartmentList.add(l);


                            }
                            newsdepartmentadapter=new DepartmentNotificationAdaptor(newsdepartmentList,Departmental_Notifications.this,Departmental_Notifications.this);
                            newsdepartmentView.setAdapter(newsdepartmentadapter);
                            newsdepartmentadapter.notifyItemInserted(position);
                            newsdepartmentadapter.notifyDataSetChanged();

                        }
                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(Departmental_Notifications.this, DetailNews.class);
        intent.putExtra("newsTitle", newsdepartmentList.get(position).getNews_title());
        intent.putExtra("newsDes", newsdepartmentList.get(position).getNewsDescription());
        intent.putExtra("newsImage", newsdepartmentList.get(position).geturi());
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem menuitem=menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                newsdepartmentadapter.getFilter().filter(s);
                return false;
            }
        });

        return true;
    }
}


