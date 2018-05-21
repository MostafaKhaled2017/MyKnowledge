//package com.mk.myknowldge.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.mk.myknowldge.R;
//import com.mk.myknowldge.model.Category;
//
//import java.util.List;
//
///**
// * Created by Pavneet_Singh on 12/20/17.
// */
//
//public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.BeanHolder> {
//
//    private List<Category> list;
//    private Context context;
//    private LayoutInflater layoutInflater;
//    private OnCategoryItemClick onCategoryItemClick;
//
//    public CategoriesAdapter(List<Category> list, Context context) {
//        layoutInflater = LayoutInflater.from(context);
//        this.list = list;
//        this.context = context;
//        this.onCategoryItemClick = (OnCategoryItemClick) context;
//    }
//
//
//        @Override
//    public BeanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = layoutInflater.inflate(R.layout.category_list_item,parent,false);
//        return new BeanHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(BeanHolder holder, int position) {
//        Log.e("bind", "onBindViewHolder: "+ list.get(position));
//        holder.textViewCategoryName.setText(list.get(position).getCategoryName());
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        TextView textViewCategoryName;
//        public BeanHolder(View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//            textViewCategoryName = itemView.findViewById(R.id.category_name);
//        }
//
//        @Override
//        public void onClick(View view) {
//            onCategoryItemClick.onCategoryClick(getAdapterPosition());
//        }
//    }
//
//    public interface OnCategoryItemClick {
//        void onCategoryClick(int pos);
//    }
//}