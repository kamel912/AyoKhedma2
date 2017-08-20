package com.ayokhedma.ayokhedma.models;

import java.util.List;

/**
 * Created by MK on 01/06/2017.
 */

public class ObjectModel {
    private String id,category,catId,name,region,street,beside,description,start1,end1,start2,end2,weekend,color, count;
    private float rate;
    private List<String> phone;

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getCatId() {
        return catId;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getStreet() {
        return street;
    }

    public String getBeside() {
        return beside;
    }

    public float getRate() {
        return rate;
    }

    public String getDescription() {
        return description;
    }

    public String getStart1() {
        return start1;
    }

    public String getEnd1() {
        return end1;
    }

    public String getStart2() {
        return start2;
    }

    public String getEnd2() {
        return end2;
    }

    public String getWeekend() {
        return weekend;
    }

    public String getColor() {
        return color;
    }

    public List<String> getPhone() {
        return phone;
    }

    public String getCount() {
        return count;
    }
}
