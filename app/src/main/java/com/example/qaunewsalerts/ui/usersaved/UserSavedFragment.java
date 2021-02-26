package com.example.qaunewsalerts.ui.usersaved;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.activities.DetailNews;
import com.example.qaunewsalerts.adaptor.FavAdaptor;
import com.example.qaunewsalerts.database.DBNews;
import com.example.qaunewsalerts.datamodals.FAVItem;

import java.util.ArrayList;
import java.util.List;

public class UserSavedFragment extends Fragment implements DetailNewsInterface {
    private RecyclerView recyclerView;
    private DBNews favDB;
    private List<FAVItem> favItemList=new ArrayList<>();
    private FavAdaptor favAdapter;

    private UserSavedViewModel savedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedViewModel =
                ViewModelProviders.of(this).get(UserSavedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved, container, false);
       // final TextView textView = root.findViewById(R.id.text_saved);
        recyclerView =root.findViewById(R.id.all_favorite_recycle);
       recyclerView.setHasFixedSize(true);
        favDB=   new DBNews(getActivity() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData();


        savedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        return root;
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
                String userid = cursor.getString(cursor.getColumnIndex(favDB.REG_ID));
                FAVItem favItem = new FAVItem(id,title,description,image, userid);
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
}
//////////////////////////////////////////////////////

