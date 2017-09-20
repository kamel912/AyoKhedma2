package com.ayokhedma.ayokhedma.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ayokhedma.ayokhedma.R;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by MK on 26/05/2017.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {

    private Context context;
    ObjectModel object = new ObjectModel();
    List<String> phones = object.getPhones();
    String link = "http://ayokhedma.com/app/images/phone/";


    public PhoneAdapter(Context context, List<String> phones) {
        this.context = context;
        this.phones = phones;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String phone = phones.get(position);
        if (phone != null) {
            String carrier = getCarrier(phone);
            String path = link + carrier + ".png";
            Glide.with(context).load(path).into(holder.phone);
        }
        holder.itemView.setTag(phone);
    }

    @Override
    public int getItemCount() {
        return this.phones.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView phone;
        private final Context context;
        public MyViewHolder(final View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phone = "tel:" + itemView.getTag().toString().trim();
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse(phone));
                    context.startActivity(intent);
                }
            });
            phone = (ImageView) itemView.findViewById(R.id.carrier_img);

        }
    }


    private String getCarrier(String string){
        StringBuilder builder = new StringBuilder();
        String phone,carrier = "";

        for (int i = 0 ; i < 3; i++){
            builder.append(string.charAt(i));
        }
        phone = builder.toString();
        switch (phone){
            case "010" :
                carrier = "vodafone";
                break;
            case "011" :
                carrier = "etisalat";
                break;
            case  "012" :
                carrier = "orange";
                break;
            case "068" :
                carrier = "landline";
                break;

        }
        return  carrier;
    }
}
