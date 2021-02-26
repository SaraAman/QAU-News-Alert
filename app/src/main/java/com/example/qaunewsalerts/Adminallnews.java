package com.example.qaunewsalerts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.qaunewsalerts.activities.DetailNews;
import com.example.qaunewsalerts.adaptor.AdminAllnewsAdaptor;
import com.example.qaunewsalerts.database.DBNews;
import com.example.qaunewsalerts.database.ReadStatusData;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.example.qaunewsalerts.ui.NewsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adminallnews extends AppCompatActivity  implements DetailNewsInterface{
    private ReadStatusData favDB;
    ProgressBar Apiprogress;
    private RecyclerView all_news_Recycleview;
    private List<News> qauNewsList =  new ArrayList<>();
    News news = new News();

    private AdminAllnewsAdaptor all_news_Adapter;
    private RecyclerView.LayoutManager all_newslayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminallnews);

        favDB = new ReadStatusData(this);
        getSupportActionBar().setTitle("All News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        all_news_Recycleview = findViewById(R.id.admin_all_news_recycle);
        Apiprogress=findViewById(R.id.api_progressBar);
        all_news_Recycleview.setHasFixedSize(true);
       // ParseNews();

    }

    @Override
    public void onResume(){
        super.onResume();
        qauNewsList.clear();
        ParseNews();

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
        return true;
    }

    @Override
    public void OnItemClick(int position) {
        Intent intent = new Intent(Adminallnews.this, DetailNews.class);
        intent.putExtra("newsTitle", qauNewsList.get(position).getNewsTitle());
        intent.putExtra("newsDes", qauNewsList.get(position).getNewsDescription());
        intent.putExtra("newsImage", qauNewsList.get(position).getNewsImage());
        startActivity(intent);
    }
    private void ParseNews() {
        SQLiteDatabase db = favDB.getReadableDatabase();
        Cursor cursor = favDB.readAllData();
        try {
                if(cursor.getCount() > 0){
                    while (cursor.moveToNext()) {
                    News news = new News();
                    news.setKey_id(cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.KEY_ID))));
                   // news.setUserID(cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.REG_ID))));
                    news.setNewsTitle(cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.NEWS_TITLE))));
                    news.setNewsDescription(cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.NEWS_DESCRIPTION))));
                    news.setNewsImage(cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.NEWS_IMAGE))));
                    news.setRead_status(Boolean.valueOf(cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.READ_STATUS)))));
                    qauNewsList.add(news);
                }}

        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

        all_news_Recycleview.setLayoutManager(new LinearLayoutManager(Adminallnews.this));
        all_news_Adapter = new AdminAllnewsAdaptor((ArrayList<News>)qauNewsList, Adminallnews.this,this);
        all_news_Recycleview.setAdapter(all_news_Adapter);
    }

//    private void APiCall(){
//        Apiprogress.setVisibility(View.VISIBLE);
//        NewsInterface newsInterface = NewsApi.getRetrofit().create(NewsInterface.class);
//        Call<List<News>> Listcall = newsInterface.getAllNews();
//        Listcall.enqueue(new Callback<List<News>>() {
//            @Override
//            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
//
//                Apiprogress.setVisibility(View.INVISIBLE);
//                qauNewsList = response.body();
//                favDB = new DBNews(Adminallnews.this);
//
//                for(int i=0; i < qauNewsList.size(); i++) {
//
//                    favDB.insertUniNews(qauNewsList.get(i).getNewsTitle(), qauNewsList.get(i).getNewsDescription(),
//                            qauNewsList.get(i).getNewsImage(), qauNewsList.get(i).getKey_id(),qauNewsList.get(i).getUserID(), false);
//                }
//
//                ParseNews();
//
//            }
//            @Override
//            public void onFailure(Call<List<News>> call, Throwable t) {
//                Toast.makeText(Adminallnews.this, "Failure Response", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
}