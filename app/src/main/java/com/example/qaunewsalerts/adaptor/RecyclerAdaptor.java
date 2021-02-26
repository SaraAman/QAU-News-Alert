package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.ViewHolder> implements Filterable {
    LayoutInflater inflater;
    DetailNewsInterface detailNewsInterface;
    List<News> newsList;
  private   Context context;
    public TextView english_d;
    public RecyclerAdaptor(List<News> newsList, Context context,DetailNewsInterface detailNewsInterface){

        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.newsList = newsList;
        this.detailNewsInterface=detailNewsInterface;

    }
    @NonNull
    @Override
    public RecyclerAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view,detailNewsInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdaptor.ViewHolder holder, final int position) {



        holder.view1.setText(newsList.get(position).getNewsTitle());
        holder.view2.setText(newsList.get(position).getNewsDescription());
        Picasso.get().load(newsList.get(position).getNewsImage()).into(holder.image1);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
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
                filterlist.addAll(newsList);
            }
            else {
                String NewsString = charSequence.toString().toLowerCase();
                for(News news: newsList){
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
            newsList.clear();
            newsList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image1;
        private TextView view1;
        private TextView view2;
        public TextView english_d;



        public ViewHolder(@NonNull View itemView, DetailNewsInterface detailNewsInterface) {
            super(itemView);
           // english_d = itemView.findViewById(R.id.searchEditText);
            image1=itemView.findViewById(R.id.image1);
            view1=itemView.findViewById(R.id.view1);
            view2=itemView.findViewById(R.id.view2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailNewsInterface.OnItemClick(getAdapterPosition());
                }
            });

        }
    }

}
