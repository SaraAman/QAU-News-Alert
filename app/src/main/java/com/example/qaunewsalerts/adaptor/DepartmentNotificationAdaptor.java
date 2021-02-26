package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.activities.AddNews;
import com.example.qaunewsalerts.datamodals.AllDepartment_news;
import com.example.qaunewsalerts.datamodals.News;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.datamodals.UserInformation;
import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DepartmentNotificationAdaptor extends RecyclerView.Adapter<DepartmentNotificationAdaptor.ViewHolder> implements Filterable {
    LayoutInflater inflater;
    DetailNewsInterface detailNewsInterface;
    ArrayList<AllDepartment_news> AllNews_Items;
    List<AllDepartment_news> newsList;
    private   Context context;

    public DepartmentNotificationAdaptor(List<AllDepartment_news> newsList, Context context,DetailNewsInterface detailNewsInterface){

        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.newsList = newsList;
        this.detailNewsInterface=detailNewsInterface;
        AllNews_Items=new ArrayList<>(newsList);
    }
    @NonNull
    @Override
    public DepartmentNotificationAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminallnewsadaptor,parent,false);
        return new DepartmentNotificationAdaptor.ViewHolder(view,detailNewsInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String userDepartment = newsList.get(position).getUserDepartment();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        if(userDepartment.equals(Constants.getSharedString("department", " ", context))){
            holder.all_news_heading.setText(newsList.get(position).getNews_title());
            holder.all_news_description.setText(newsList.get(position).getNewsDescription());
            Picasso.get().load(newsList.get(position).geturi()).into(holder.allnews_image);
        }
       else{
            holder.itemView.setVisibility(View.GONE);
        }
//        Query matchQuery = ref.child("UserInformation").orderByChild("department").equalTo(userDepartment);
//        matchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
////                    UserInformation userInformation = appleSnapshot.getValue(UserInformation.class);
//
////                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



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
            List<AllDepartment_news> filterlist=new ArrayList<>();
            if(charSequence== null||charSequence.length()==0)
            {
                filterlist.addAll(AllNews_Items);
            }
            else {
                String NewsString = charSequence.toString().toLowerCase();
                for(AllDepartment_news news: AllNews_Items){
                    if(news.getNews_title().toLowerCase().trim().contains(NewsString.trim())){
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
        private ImageView allnews_image;
        private TextView all_news_heading;
        private TextView all_news_description;
       private Button saved_news;



        public ViewHolder(@NonNull View itemView, DetailNewsInterface detailNewsInterface) {
            super(itemView);
            allnews_image=itemView.findViewById(R.id.allnews_image);
            all_news_heading=itemView.findViewById(R.id.all_news_heading);
            all_news_description=itemView.findViewById(R.id.all_news_description);
            saved_news=itemView.findViewById(R.id.save_button);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailNewsInterface.OnItemClick(getAdapterPosition());
                }
            });

        }
    }

}






