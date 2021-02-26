package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.qaunewsalerts.activities.AddNews;
import com.example.qaunewsalerts.activities.Signup;
import com.example.qaunewsalerts.database.DBNews;
import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.datamodals.UserInformation;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DBAdaptor  extends RecyclerView.Adapter<DBAdaptor.ViewHolder> implements Filterable {

    ArrayList<News> favItems;
    ArrayList<News> AllNews_Items;
    private Context context;
    private DBNews favDB;
    UserInformation userInformation;
    DetailNewsInterface detailNewsInterface;
    public DBAdaptor(ArrayList<News> favItems, Context context,DetailNewsInterface detailNewsInterface) {
        this.favItems = favItems;
        this.context = context;
        this.detailNewsInterface=detailNewsInterface;
        AllNews_Items=new ArrayList<>(favItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new DBNews(context);
        //create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            createTableOnFirstStart();
        }

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new DBAdaptor.ViewHolder(view,detailNewsInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final News news=favItems.get(position);
        readCursorData(news,holder);
        holder.view1.setText(favItems.get(position).getNewsTitle());
        holder.view2.setText(favItems.get(position).getNewsDescription());
        Picasso.get().load(favItems.get(position).getNewsImage()).into(holder.image1);

    }

    @Override
    public int getItemCount() {
        return favItems.size();
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
        private ImageView image1;
        private TextView view1;
        private TextView view2;
        private Button favBtn;
        public ViewHolder(@NonNull View itemView,DetailNewsInterface detailNewsInterface) {
            super(itemView);

            image1=itemView.findViewById(R.id.image1);
            view1=itemView.findViewById(R.id.view1);
            view2=itemView.findViewById(R.id.view2);
            favBtn = itemView.findViewById(R.id.fav_button);
            //            favBtn.setTag("uncheck");
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String useid=Constants.getSharedString("userId","", context);
                    int position = getAdapterPosition();
                    News news = favItems.get(position);
                    if(news.getFavStatus().equals("0")){
                        news.setFavStatus("1");
                        // favBtn.setTag("checked");
                        favDB.insertIntoTheDatabase(news.getNewsTitle(),news.getNewsDescription(),
                                news.getNewsImage(),news.getKey_id(),news.getFavStatus(),useid);
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                    }
                    else {
                        //  favBtn.setTag("uncheck");
                        news.setFavStatus("0");
                        favDB.remove_fav(news.getKey_id());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                    }

                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailNewsInterface.OnItemClick(getAdapterPosition());
                }
            });
            //add to fav btn

        }
    }

    private void createTableOnFirstStart() {
       // favDB.insertEmpty();
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(News newsItem, ViewHolder viewHolder) {
        Cursor cursor = favDB.read_all_data(newsItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(DBNews.FAVORITE_STATUS));
                newsItem.setFavStatus(item_fav_status);
                Log.d("item_fav_status",item_fav_status);
                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_border_black_24dp);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

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
