package com.example.qaunewsalerts.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.database.DBNews;
import com.example.qaunewsalerts.database.ReadStatusData;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.datamodals.UserInformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminAllnewsAdaptor extends   RecyclerView.Adapter<AdminAllnewsAdaptor.ViewHolder> implements Filterable  {

    ArrayList<News> favItems;
    ArrayList<News> AllNews_Items;
    private Context context;
    private ReadStatusData favDB;
 Boolean rstatus;
    UserInformation userInformation;
    DetailNewsInterface detailNewsInterface;

    public AdminAllnewsAdaptor(ArrayList<News> favItems, Context context,DetailNewsInterface detailNewsInterface){
        this.favItems = favItems;
        this.context = context;
        this.detailNewsInterface=detailNewsInterface;
        AllNews_Items=new ArrayList<>(favItems);
    }
    @NonNull
    @Override
    public AdminAllnewsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        favDB = new ReadStatusData(context);
//        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
//        boolean firstStart = prefs.getBoolean("firstStart", true);
//        if (firstStart) {
//            createTableOnFirstStart();
//        }
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminallnewsadaptor,parent,false);
        return new AdminAllnewsAdaptor.ViewHolder(view,detailNewsInterface);
    }


    //@SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //final News news=favItems.get(position);
        //readCursorData(news,holder);

        holder.adminview1.setText(favItems.get(position).getNewsTitle());
        holder.adminview2.setText(favItems.get(position).getNewsDescription());
        Picasso.get().load(favItems.get(position).getNewsImage()).into(holder.imageadmin1);
        if ( favItems.get(position).getRead_status().equals(true)) {
            
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));


        } else {

            holder.itemView.setBackgroundColor(Color.parseColor("#e8eced"));
            holder.adminview2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }

    }



    @Override
    public int getItemCount(){return favItems.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<News> filterlist=new ArrayList<>();
            if(charSequence== null||charSequence.length()==0)
            {
                filterlist.addAll(AllNews_Items);
            }
            else {
                String NewsString = charSequence.toString().toLowerCase();
                for(News news: AllNews_Items){
                    if(news.getNewsTitle().toLowerCase().trim().contains(NewsString.trim())){
                        filterlist.add(news);
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=filterlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            favItems.clear();
            favItems.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageadmin1;
        private TextView adminview1;
        private TextView adminview2;
        private Button favBtn;

        public ViewHolder(@NonNull View itemView,DetailNewsInterface detailNewsInterface) {
            super(itemView);

            imageadmin1=itemView.findViewById(R.id.allnews_image);
            adminview1=itemView.findViewById(R.id.all_news_heading);
            adminview2=itemView.findViewById(R.id.all_news_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailNewsInterface.OnItemClick(getAdapterPosition());
                    int position = getAdapterPosition();
                    News news = favItems.get(position);
                    if(news.getRead_status().equals(false)){
                        favDB.update_read_status(news.getKey_id());
                    }

                }
            });
            //add to fav btn

        }
    }


//    private void readCursorData(News newsItem, AdminAllnewsAdaptor.ViewHolder viewHolder) {
//        Cursor cursor = favDB.read_all_data(newsItem.getKey_id());
//        SQLiteDatabase db = favDB.getReadableDatabase();
//        try {
//            while (cursor.moveToNext()) {
//                Boolean item_fav_status = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(String.valueOf(DBNews.READ_STATUS))));
//                newsItem.setRead_status(item_fav_status);
//
//            }
//        } finally {
//            if (cursor != null && cursor.isClosed())
//                cursor.close();
//            db.close();
//        }
//
//    }
    private void createTableOnFirstStart() {
       // favDB.insertEmpty();
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

}

/////////////////////////////////////////////
