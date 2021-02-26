package com.example.qaunewsalerts.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qaunewsalerts.adaptor.DBAdaptor;
import com.example.qaunewsalerts.activities.DetailNews;
import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.NewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.adaptor.SliderAdapter;
import com.example.qaunewsalerts.ui.NewsApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class GuestHomeFragment extends Fragment implements DetailNewsInterface {
    private RecyclerView mRecycleview;
    private List<News> qauNewsList;
    private DBAdaptor mAdapter;
    private RecyclerView.LayoutManager mlayoutManager;
    ViewPager viewPager;
    ProgressBar Apiprogress;
EditText searchEditText;

    public GuestHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_guest_home, container, false);

        mRecycleview = rootview.findViewById(R.id.recycle);
        mRecycleview.setHasFixedSize(true);
        searchEditText = rootview.findViewById(R.id.searchEditText);
        viewPager = rootview.findViewById(R.id.slider);
        Apiprogress=rootview.findViewById(R.id.api_progressBar);
        SliderAdapter sliderAdapter = new SliderAdapter(getContext());
        viewPager.setAdapter(sliderAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimetask(), 2000, 3000);
        ApiCall();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        return rootview;
    }


    private void ParseNews() {
        mRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new DBAdaptor((ArrayList<News>) qauNewsList, getContext(),this);
        mRecycleview.setAdapter(mAdapter);
    }

private void ApiCall(){
    Apiprogress.setVisibility(View.VISIBLE);
    NewsInterface newsInterface = NewsApi.getRetrofit().create(NewsInterface.class);
    Call<List<News>> Listcall = newsInterface.getAllNews();
    Listcall.enqueue(new Callback<List<News>>() {
        @Override
        public void onResponse(Call<List<News>> call, Response<List<News>> response) {

            Apiprogress.setVisibility(View.INVISIBLE);
            qauNewsList = response.body();
            ParseNews();
        }

        @Override
        public void onFailure(Call<List<News>> call, Throwable t) {

            Toast.makeText(getActivity(), "Failure Response", Toast.LENGTH_SHORT).show();
        }
    });


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
            if (getActivity() == null)
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