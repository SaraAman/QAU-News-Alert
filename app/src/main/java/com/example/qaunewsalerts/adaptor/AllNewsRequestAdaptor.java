package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.DetailNewsInterface;
import com.example.qaunewsalerts.NewRequestDetail;
import com.example.qaunewsalerts.datamodals.NewsRequest;
import com.example.qaunewsalerts.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllNewsRequestAdaptor extends RecyclerView.Adapter<AllNewsRequestAdaptor.ViewHolder> {

    private List<NewsRequest> listData;
    private Context context;
    NewRequestDetail newRequestDetail;

    public AllNewsRequestAdaptor(List<NewsRequest> listData, Context context, NewRequestDetail newRequestDetail) {
        this.listData = listData;
        this.context = context;
        this.newRequestDetail=newRequestDetail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allnewsrequestadaptor, parent, false);
        return new ViewHolder(view,newRequestDetail);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewsRequest ld = listData.get(position);
        Picasso.get().load(listData.get(position).geturi()).into(holder.all_news_request_image);
        holder.all_news_request_heading.setText(ld.getNews_title());
        holder.all_news_request_description.setText(ld.getNewsDescription());
        holder.all_news_request_category.setText(ld.getnewsCategory());
        holder.all_news_User_deparment.setText(ld.getUserDepartment());
        holder.all_news_user_id.setText(ld.getUserID());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView all_news_request_image;
        private TextView all_news_request_heading;
        private TextView all_news_request_description;
        private TextView all_news_request_category;
        private TextView all_news_User_deparment;
        private TextView all_news_user_id;

        public ViewHolder(@NonNull View itemView, NewRequestDetail newRequestDetail) {
            super(itemView);
            all_news_request_image = itemView.findViewById(R.id.all_news_request_image);
            all_news_request_heading = itemView.findViewById(R.id.all_news_request_heading);
            all_news_request_description = itemView.findViewById(R.id.all_news_request_description);
            all_news_request_category = itemView.findViewById(R.id.all_news_request_category);
            all_news_User_deparment = itemView.findViewById(R.id.all_news_request_User_department);
            all_news_user_id = itemView.findViewById(R.id.all_news_request_user_id);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newRequestDetail.OnItemClick(getAdapterPosition());
                }
            });

        }
    }
}

