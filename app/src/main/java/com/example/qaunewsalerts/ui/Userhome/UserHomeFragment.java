package com.example.qaunewsalerts.ui.Userhome;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.qaunewsalerts.activities.AddNews;
import com.example.qaunewsalerts.activities.AllNews;
import com.example.qaunewsalerts.activities.Departmental_Notifications;
import com.example.qaunewsalerts.activities.DetailNews;
import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.adaptor.GridViewAdaptor;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.NewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.adaptor.SliderAdapter;
import com.example.qaunewsalerts.ui.NewsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHomeFragment extends Fragment  implements DetailNewsInterface {
    TextView viewall;
    ViewPager viewPager;
    Button department_notifications;
    private UserHomeViewModel homeViewModel;

    private RecyclerView mRecycleview;
    private List<News> qauNewsList;
    private GridViewAdaptor mAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        homeViewModel =
                ViewModelProviders.of(this).get(UserHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_userhome, container, false);
        department_notifications=root.findViewById(R.id.dept_noti);
        mRecycleview = root.findViewById(R.id.grid_recycle);
       //


        NewsInterface newsInterface = NewsApi.getRetrofit().create(NewsInterface.class);
        Call<List<News>> Listcall = newsInterface.getAllNews();
        Listcall.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                qauNewsList = response.body();
                ParseNews();
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure Response", Toast.LENGTH_SHORT).show();
            }
        });

        department_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Departmental_Notifications.class));
            }
        });
        viewall=root.findViewById(R.id.view_all_news);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllNews.class));
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener()
       {
            @Override
           public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddNews.class));
           }
       });

        viewPager=root.findViewById(R.id.slider);
        SliderAdapter sliderAdapter=new SliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new UserHomeFragment.MyTimetask(), 2000, 3000);



        return root;
    }


    private void ParseNews() {
        int numberOfColumns = 2;
        mRecycleview.setLayoutManager(new GridLayoutManager(getContext(), numberOfColumns));
        mAdapter = new GridViewAdaptor(qauNewsList, getContext(),this);
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
