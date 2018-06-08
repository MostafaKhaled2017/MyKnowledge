package com.mk.myknowldge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.mk.myknowldge.R;
import com.mk.myknowldge.activities.NotesActivity;
import com.mk.myknowldge.helpers.DatabaseHelper;
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
        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.category_name);
            dot = view.findViewById(R.id.dot);
            relativeLayout = view.findViewById(R.id.categories_recycler_view);
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
        final Category category = categoriesList.get(position);
        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        holder.name.setText(category.getName());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NotesActivity.class);
                i.putExtra("category_name",category.getName());
                i.putExtra("category_id",category.getId());
                Log.v("logging", "category ID in adapter is : " + category.getId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

}