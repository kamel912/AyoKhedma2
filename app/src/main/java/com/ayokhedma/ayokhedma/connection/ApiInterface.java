package com.ayokhedma.ayokhedma.connection;

import com.ayokhedma.ayokhedma.models.CategoryModel;
import com.ayokhedma.ayokhedma.models.ObjectModel;
import com.ayokhedma.ayokhedma.models.RegionModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by MK on 21/07/2017.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("category.php")
    Call<List<CategoryModel>> getMainCategories(@Field("limit") String limit);

    @GET("categoty.php")
    Call<List<CategoryModel>> getAllCategories();

    @FormUrlEncoded
    @POST("category.php")
    Call<List<ObjectModel>> getAllObjects(@Field("catid") String catid);

    @FormUrlEncoded
    @POST("category.php")
    Call<List<ObjectModel>> getRegionObjects(@Field("catid") String catid, @Field("regid") String regid);

    @GET("region.php")
    Call<List<RegionModel>> getRegions();

    @FormUrlEncoded
    @POST("object.php")
    Call<ObjectModel> getObject(@Field("objid") String objid);

    @FormUrlEncoded
    @POST("search.php")
    Call<List<ObjectModel>> search(@Field("search") String search);
}
