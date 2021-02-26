package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.database.DBNews;
import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.datamodals.FAVItem;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavAdaptor extends RecyclerView.Adapter<FavAdaptor.ViewHolder> {
    private Context context;
    private List<FAVItem> favItemList;
    private DBNews favDB;
    DetailNewsInterface detailNewsInterface;


    public FavAdaptor(Context context, List<FAVItem> favItemList,DetailNewsInterface detailNewsInterface) {
        this.context = context;
        this.favItemList = favItemList;
        this.detailNewsInterface=detailNewsInterface;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favoriteadaptor,
                parent, false);
        favDB = new DBNews(context);
        return new ViewHolder(view,detailNewsInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FavAdaptor.ViewHolder holder, int position) {

        if(favItemList.get(position).getUserId().equals(Constants.getSharedString("userId","", context))) {
            holder.view1.setText(favItemList.get(position).getNewsTitle());
            holder.view2.setText(favItemList.get(position).getNewsDescription());
            Picasso.get().load(favItemList.get(position).getNewsImage()).into(holder.image1);
        }
        else{
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return favItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image1;
        private TextView view1;
        private TextView view2;
        private Button favBtn;
        public ViewHolder(@NonNull View itemView, DetailNewsInterface detailNewsInterface) {
            super(itemView);
            image1=itemView.findViewById(R.id.fav_adaptor_image);
            view1=itemView.findViewById(R.id.fav_adaptor_title);
            view2=itemView.findViewById(R.id.fav_adaptor_description);
            favBtn = itemView.findViewById(R.id.fav_adaptor_button);
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                   final FAVItem news = favItemList.get(position);
                   favDB.remove_fav(news.getKey_id());
                   removeItem(position);

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   detailNewsInterface.OnItemClick(getAdapterPosition());
                }
            });

        }
    }

    private void removeItem(int position) {
        favItemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,favItemList.size());
    }
}
