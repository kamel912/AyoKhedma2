package com.ayokhedma.ayokhedma.models;

/**
 * Created by MK on 12/07/2017.
 */

public class RateModel {
    private String objid,userid;
    private float rate;

    public RateModel(String objid, String userid, float rate) {
        this.objid = objid;
        this.userid = userid;
        this.rate = rate;
    }

    public RateModel(String objid, String userid) {
        this.objid = objid;
        this.userid = userid;
    }

    public String getObjid() {
        return objid;
    }

    public String getUserid() {
        return userid;
    }

    public float getRate() {
        return rate;
    }
}
