package com.example.qaunewsalerts.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.NewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.adaptor.AllnewsRecyclerAdaptopr;
import com.example.qaunewsalerts.ui.NewsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNews extends AppCompatActivity implements DetailNewsInterface {
    ProgressBar Apiprogress;
    private RecyclerView all_news_Recycleview;
    private List<News> qauNewsList;
    private AllnewsRecyclerAdaptopr all_news_Adapter;
    private RecyclerView.LayoutManager all_newslayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        getSupportActionBar().setTitle("All News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        all_news_Recycleview = findViewById(R.id.all_news_recycle);
        Apiprogress=findViewById(R.id.api_progressBar);
        all_news_Recycleview.setHasFixedSize(true);


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
                all_news_Adapter.getFilter().filter(s);
                return false;
            }
        });
        APiCall();
        return true;
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(AllNews.this, DetailNews.class);
        intent.putExtra("newsTitle", qauNewsList.get(position).getNewsTitle());
        intent.putExtra("newsDes", qauNewsList.get(position).getNewsDescription());
        intent.putExtra("newsImage", qauNewsList.get(position).getNewsImage());
        startActivity(intent);
    }
    private void ParseNews() {
        all_news_Recycleview.setLayoutManager(new LinearLayoutManager(AllNews.this));
        all_news_Adapter = new AllnewsRecyclerAdaptopr((ArrayList<News>)qauNewsList, AllNews.this,this);
        all_news_Recycleview.setAdapter(all_news_Adapter);
    }
    private void APiCall(){
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
                Toast.makeText(AllNews.this, "Failure Response", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
