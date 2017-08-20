package com.ayokhedma.ayokhedma.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MK on 23/07/2017.
 */

public class ApiClient {
    private static final String BASE_URL = "http://ayokhedma.com/app/";
    private static Retrofit retrofit;
    public static Retrofit getApiClient(){
        if(retrofit == null) {
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
        }
        return retrofit;
    }
}
