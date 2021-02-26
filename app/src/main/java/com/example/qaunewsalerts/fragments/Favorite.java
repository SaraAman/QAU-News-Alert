 package com.example.qaunewsalerts.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.database.DBNews;
import com.example.qaunewsalerts.activities.DetailNews;
import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.datamodals.FAVItem;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.adaptor.FavAdaptor;

import java.util.ArrayList;
import java.util.List;

 public class Favorite extends Fragment  implements DetailNewsInterface {


     private RecyclerView recyclerView;
     private DBNews favDB;
     private List<FAVItem> favItemList=new ArrayList<>();
     private FavAdaptor favAdapter;

    public Favorite() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView =rootview. findViewById(R.id.all_favorite_recycle);
        recyclerView.setHasFixedSize(true);
        favDB=   new DBNews(getActivity() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData();
        return rootview;
    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         return false;
     }

     @Override
     public void OnItemClick(int position) {

         Intent intent = new Intent(getActivity(), DetailNews.class);
         intent.putExtra("newsTitle", favItemList.get(position).getNewsTitle());
         intent.putExtra("newsDes", favItemList.get(position).getNewsDescription());
         intent.putExtra("newsImage", favItemList.get(position).getNewsImage());
         startActivity(intent);

     }
     private void loadData() {
         if (favItemList != null) {
             favItemList.clear();
         }
         SQLiteDatabase db = favDB.getReadableDatabase();
         Cursor cursor = favDB.select_all_favorite_list();
         try {
             while (cursor.moveToNext()) {
                 String title = cursor.getString(cursor.getColumnIndex(favDB.NEWS_TITLE));
                String id = cursor.getString(cursor.getColumnIndex(favDB.KEY_ID));
                 String description=cursor.getString(cursor.getColumnIndex(favDB.NEWS_DESCRIPTION));
                 String image = cursor.getString(cursor.getColumnIndex(favDB.NEWS_IMAGE));
                 String userId = cursor.getString(cursor.getColumnIndex(favDB.REG_ID));
                FAVItem favItem = new FAVItem(id,title,description,image, userId);
                 favItemList.add(favItem);
             }
         } finally {
             if (cursor != null && cursor.isClosed())
                 cursor.close();
             db.close();
         }
         favAdapter = new FavAdaptor(getActivity(), favItemList,this);
         recyclerView.setAdapter(favAdapter);
     }
 }
