package com.ayokhedma.ayokhedma.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ayokhedma.ayokhedma.models.RegionModel;

import java.util.List;

/**
 * Created by MK on 03/06/2017.
 */

public class RegionAdapter extends ArrayAdapter<RegionModel> {

    Context context;
    List<RegionModel> regions;

    public RegionAdapter(Context context, int textViewResourceId,
                         List<RegionModel> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.regions = values;
    }


    public int getCount(){
        return regions.size();
    }

    public RegionModel getItem(int position){
        return regions.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setTextSize(20);
        view.setGravity(Gravity.CENTER);
        view.setText(regions.get(position).getName());
        return view;
    }

    //View of Spinner on dropdown Popping

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView view = new TextView(context);
        view.setTextColor(Color.BLACK);
        view.setTextSize(20);
        view.setBackgroundColor(Color.WHITE);
        view.setGravity(Gravity.CENTER);
        view.setText(regions.get(position).getName());

        return view;
    }

}
