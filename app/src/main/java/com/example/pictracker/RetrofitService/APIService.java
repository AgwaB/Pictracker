package com.example.pictracker.RetrofitService;

import com.example.pictracker.LoginInfo.FacebookInfo;
import com.example.pictracker.LoginInfo.KakaoInfo;
import com.example.pictracker.LoginInfo.NaverInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by MECSL on 2017-08-31.
 */

public interface APIService {


    @GET("/naver")
    Call<NaverInfo> createNaverInfo(@Body NaverInfo naverInfo);

    @GET("/facebook")
    Call<FacebookInfo> createFacebookInfo(@Body FacebookInfo facebookInfo);

    @GET("/kakao")
    Call<KakaoInfo> createKakaoInfo(@Body KakaoInfo kakaoInfo);

//    @POST("/schedule")
//    @FormUrlEncoded
//    Call<SchedulePack> saveSchedule(@Body SchedulePack schedulePack);
//
//    @PUT("/schedule/{id}")
//    @FormUrlEncoded
//    Call<SchedulePack> updateSchedule(@Body SchedulePack schedulePack);

}
