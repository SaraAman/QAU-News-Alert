package com.example.qaunewsalerts.ui.adminnewsrequest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.Faculty;
import com.example.qaunewsalerts.NewRequestDetail;
import com.example.qaunewsalerts.NewsRequestDetail;
import com.example.qaunewsalerts.activities.AllNews;
import com.example.qaunewsalerts.activities.DetailNews;
import com.example.qaunewsalerts.adaptor.AllNewsRequestAdaptor;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminNewsRequestFragment extends Fragment  implements NewRequestDetail {
    private List<NewsRequest> newsRequestList;
    private RecyclerView newsRequestView;
    DatabaseReference dReference, dref;
    private AllNewsRequestAdaptor newsRequestadapter;
    int position = 0;
    private AdminNewsRequestViewModel adminNCViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        adminNCViewModel =
                ViewModelProviders.of(this).get(AdminNewsRequestViewModel.class);
        View rootview = inflater.inflate(R.layout.fragment_adminnewsreq, container, false);

        newsRequestView=rootview.findViewById(R.id.news_request_recycle);
        newsRequestView.setHasFixedSize(true);
        newsRequestView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRequestList=new ArrayList<>();
        dReference = FirebaseDatabase.getInstance().getReference().child("Requests");
        dReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        NewsRequest l=npsnapshot.getValue(NewsRequest.class);
                        newsRequestList.add(l);
                    }
                    newsRequestadapter=new AllNewsRequestAdaptor(newsRequestList,getContext(),AdminNewsRequestFragment.this);
                    newsRequestView.setAdapter(newsRequestadapter);
                    newsRequestadapter.notifyItemInserted(position);
                    newsRequestadapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return rootview;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void OnItemClick(int position) {

        Intent intent = new Intent(getActivity(), NewsRequestDetail.class);
        intent.putExtra("newsTitle", newsRequestList.get(position).getNews_title());
        intent.putExtra("newsDes", newsRequestList.get(position).getNewsDescription());
        intent.putExtra("UserID", newsRequestList.get(position).getUserID());
        intent.putExtra("UserDepartment", newsRequestList.get(position).getUserDepartment());
        intent.putExtra("newsImage", newsRequestList.get(position).geturi());
        intent.putExtra("newsCategory", newsRequestList.get(position).getnewsCategory());
        startActivity(intent);
    }
}

