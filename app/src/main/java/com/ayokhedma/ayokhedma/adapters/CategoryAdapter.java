package com.ayokhedma.ayokhedma.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.models.CategoryModel;
import com.ayokhedma.ayokhedma.ui.CategoryActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MK on 26/05/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private Context context;
    List<CategoryModel> categories = new ArrayList<>();
    private String image_path = "http://www.ayokhedma.com/app/images/category/";



    public CategoryAdapter(Context context, List<CategoryModel> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoryModel category = categories.get(position);
        holder.cat_name.setText(category.getName());
        String path = image_path + category.getId() + ".png";
        Glide.with(context).load(path).into(holder.cat_pic);

        holder.itemView.setTag(category);

    }

    @Override
    public int getItemCount() {
        return this.categories.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView cat_name;
        ImageView cat_pic;
        private final Context context;

        public MyViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CategoryModel category = (CategoryModel) itemView.getTag();
                    String catId = category.getId();
                    String catName = category.getName();
                    Intent intent = new Intent(context, CategoryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", catId);
                    bundle.putString("name", catName);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    ((Activity)context).finish();

                }
            });

            cat_name = (TextView) itemView.findViewById(R.id.cat_name);
            cat_pic = (ImageView) itemView.findViewById(R.id.cat_img);
        }
    }
}
