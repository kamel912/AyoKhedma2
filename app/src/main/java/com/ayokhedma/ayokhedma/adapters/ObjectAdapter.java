package com.ayokhedma.ayokhedma.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.glide.CircleTransform;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.ayokhedma.ayokhedma.ui.ObjectActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MK on 03/06/2017.
 */

public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.MyViewHolder>{

    private Context context;
    private List<ObjectModel> objects = new ArrayList<>();
    private String image_path = "http://www.fatmanoha.com/ayokhedma/images/object/";






    public ObjectAdapter(Context context, List<ObjectModel> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.obj_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ObjectModel object = objects.get(position);
        holder.name.setText(object.getCategory() + " "+ object.getName());
        holder.region.setText("المنطقة : " + object.getRegion());
        holder.count.setText("عدد التقييمات : " + object.getCount());
       // holder.region.setBackgroundColor(Color.parseColor(object.getColor()));
        holder.address.setText("شارع " + object.getStreet() + " " + object.getBeside());
        holder.rate.setText(Float.toString(object.getRate()));
        holder.rating.setRating(object.getRate());
        String path = image_path + object.getId() + ".png";

        Glide.with(context).load(path).error(R.drawable.defaulty).transform(new CircleTransform(context)).into(holder.obj_pic);


        holder.itemView.setTag(object);

    }

    @Override
    public int getItemCount() {
        return this.objects.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,region,address,rate,count;
        ImageView obj_pic;
        RatingBar rating;
        private final Context context;

        public MyViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();

            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ObjectModel object = (ObjectModel) itemView.getTag();
                    String name = object.getName();
                    String id = object.getId();
                    String category = object.getCategory();
                    //String count = object.getCount();
                    Intent intent = new Intent(context, ObjectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("id",id);
                    bundle.putString("category",category);
                    //bundle.putString("count",count);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                }
            });


            name = (TextView) itemView.findViewById(R.id.obj_name);
            rate = (TextView) itemView.findViewById(R.id.obj_rate);
            count = (TextView) itemView.findViewById(R.id.rate_count);
            region = (TextView) itemView.findViewById(R.id.obj_reg);
            address = (TextView) itemView.findViewById(R.id.obj_add);
            obj_pic = (ImageView) itemView.findViewById(R.id.obj_Img);
            rating = (RatingBar) itemView.findViewById(R.id.obj_rating);
            rating.setNumStars(5);
        }
    }



}
