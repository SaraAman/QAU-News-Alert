package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.database.ReadStatusData;
import com.example.qaunewsalerts.datamodals.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminGridViewAdaptor extends   RecyclerView.Adapter<AdminGridViewAdaptor.ViewHolder>  {

    LayoutInflater inflater;
    DetailNewsInterface detailNewsInterface;
    List<News> newsList;
    private ReadStatusData favDB;
    private   Context context;

    public AdminGridViewAdaptor(List<News> newsList, Context context, DetailNewsInterface detailNewsInterface){

        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.newsList = newsList;
        this.detailNewsInterface=detailNewsInterface;

    }
    @NonNull
    @Override
    public AdminGridViewAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new ReadStatusData(context);
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admingridviewadaptor,parent,false);
        return new ViewHolder(view,detailNewsInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if(position<=5)
        {   holder.all_news_heading.setText(newsList.get(position).getNewsTitle());
            holder.all_news_description.setText(newsList.get(position).getNewsDescription());
            Picasso.get().load(newsList.get(position).getNewsImage()).into(holder.allnews_image);
        }


    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView allnews_image;
        private TextView all_news_heading;
        private TextView all_news_description;

        public ViewHolder(@NonNull View itemView, DetailNewsInterface detailNewsInterface) {
            super(itemView);
            allnews_image=itemView.findViewById(R.id.allnews_image);
            all_news_heading=itemView.findViewById(R.id.all_news_heading);
            all_news_description=itemView.findViewById(R.id.all_news_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailNewsInterface.OnItemClick(getAdapterPosition());
                }
            });

        }
    }
    private void createTableOnFirstStart() {
        // favDB.insertEmpty();
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}



