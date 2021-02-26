package com.example.qaunewsalerts.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qaunewsalerts.sharedpreferences.Constants;
import com.example.qaunewsalerts.R;
import com.example.qaunewsalerts.datamodals.UserInformation;

import java.util.ArrayList;
import java.util.List;


public class AllDepartmentAdaptor extends RecyclerView.Adapter<AllDepartmentAdaptor.ViewHolder> implements Filterable {

    private List<String> title;
    private Context context;
    ArrayList<String> AllNews_Items;
    private UserInformation userInformation;
    private int selectedPosition = -1;

    public AllDepartmentAdaptor(List<String> titleitems, Context context) {
        this.title = titleitems;
        this.context = context;
        AllNews_Items=new ArrayList<>(titleitems);
    }

    @NonNull
    @Override
    public AllDepartmentAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.department_all_recycler,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.all_department_heading.setText(title.get(position));
        holder.DepartmentCheck.setChecked(selectedPosition == position);
        if(Constants.getSharedString("department","",context).equals(title.get(position)))
        {
            holder.DepartmentCheck.setChecked(true);
        }


    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String > filterlist=new ArrayList<>();
            if(charSequence== null||charSequence.length()==0)
            {
                filterlist.addAll(AllNews_Items);
            }
            else {
                String NewsString = charSequence.toString().toLowerCase();
                for(String news: AllNews_Items){
                    if(news.toLowerCase().trim().contains(NewsString.trim())){
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
            title.clear();
            title.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView all_department_heading;
        private CheckBox DepartmentCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            all_department_heading=itemView.findViewById(R.id.all_department_heading);
            DepartmentCheck=itemView.findViewById(R.id.department_check_box);


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
