package com.example.qaunewsalerts.ui.adminhome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.qaunewsalerts.Adminallnews;
import com.example.qaunewsalerts.NewRequestDetail;
import com.example.qaunewsalerts.activities.AdminAddNews;
import com.example.qaunewsalerts.activities.AllNews;
import com.example.qaunewsalerts.activities.Departmental_Notifications;
import com.example.qaunewsalerts.activities.DetailNews;
import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.adaptor.AdminGridViewAdaptor;
import com.example.qaunewsalerts.adaptor.AllNewsRequestAdaptor;
import com.example.qaunewsalerts.adaptor.GridViewAdaptor;
import com.example.qaunewsalerts.database.DBNews;
import com.example.qaunewsalerts.database.ReadStatusData;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.NewsInterface;
import com.example.qaunewsalerts.activities.PublishNews;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.adaptor.SliderAdapter;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.ui.NewsApi;
import com.example.qaunewsalerts.ui.adminnewsrequest.AdminNewsRequestFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminHomeFragment extends Fragment  implements DetailNewsInterface {
    TextView viewall;
    ViewPager viewPager;
    Button department_notifications;
    private AdminHomeViewModel adminHomeViewModel;
    private RecyclerView mRecycleview;
    private List<News> qauNewsList;
    private ReadStatusData favDB;
    private AdminGridViewAdaptor mAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        adminHomeViewModel =
                ViewModelProviders.of(this).get(AdminHomeViewModel.class);
        View root = inflater.inflate(R.layout.adminfragment_home, container, false);
        department_notifications=root.findViewById(R.id.dept_noti);
        mRecycleview = root.findViewById(R.id.grid_recycle);



        NewsInterface newsInterface = NewsApi.getRetrofit().create(NewsInterface.class);
        Call<List<News>> Listcall = newsInterface.getAllNews();
        Listcall.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                qauNewsList = response.body();

                favDB = new ReadStatusData(getContext());

                for(int i=0; i < qauNewsList.size(); i++) {
                   favDB.insertUniNews(qauNewsList.get(i).getNewsTitle(), qauNewsList.get(i).getNewsDescription(),
                            qauNewsList.get(i).getNewsImage(), qauNewsList.get(i).getKey_id(), false);
                }

                ParseNews();
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure Response", Toast.LENGTH_SHORT).show();
            }
        });

//        department_notifications.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), Departmental_Notifications.class));
//            }
//        });
        viewall=root.findViewById(R.id.view_all_news);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Adminallnews.class));
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.adminaddnews);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminAddNews.class));
            }
        });

        viewPager=root.findViewById(R.id.adminslider);
        SliderAdapter sliderAdapter=new SliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new AdminHomeFragment.MyTimetask(), 2000, 3000);



        return root;
    }


    private void ParseNews() {
        int numberOfColumns = 2;
        mRecycleview.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        mAdapter = new AdminGridViewAdaptor(qauNewsList, getContext(),this);
        mRecycleview.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailNews.class);
        intent.putExtra("newsTitle", qauNewsList.get(position).getNewsTitle());
        intent.putExtra("newsDes", qauNewsList.get(position).getNewsDescription());
        intent.putExtra("newsImage", qauNewsList.get(position).getNewsImage());
        startActivity(intent);
    }

    public class MyTimetask extends TimerTask {

        @Override
        public void run() {
            if(getActivity()==null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }


            });

        }


    }


}
