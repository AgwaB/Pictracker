package com.example.pictracker.RetrofitService;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by MECSL on 2017-08-31.
 */

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
