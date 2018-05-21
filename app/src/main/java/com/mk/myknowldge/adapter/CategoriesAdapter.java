package com.mk.myknowldge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mk.myknowldge.R;
import com.mk.myknowldge.model.Category;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {
    //TODO : edit
    private Context context;
    private List<Category> categoriesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView dot;
        public TextView timestamp;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.dialog_value);
            dot = view.findViewById(R.id.dot);
            timestamp = view.findViewById(R.id.timestamp);
        }
    }


    public CategoriesAdapter(Context context, List<Category> categoriesList) {
        this.context = context;
        this.categoriesList = categoriesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Category category = categoriesList.get(position);

        holder.name.setText(category.getName());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

}