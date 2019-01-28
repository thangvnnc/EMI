package com.gmail.thangvnnc.emi.DBServer.API;

import com.gmail.thangvnnc.emi.Model.MResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @POST("support")
    @FormUrlEncoded
    Call<MResponse> saveSupport(@Field("id") String id,
                                @Field("content") String content,
                                @Field("created_at") Long createdAt);

    @POST("device/android")
    @FormUrlEncoded
    Call<MResponse> saveDevice(@Field("id") String id,
                               @Field("androidId") String androidId,
                               @Field("created_at") Long createdAt);

    @POST("admod")
    @FormUrlEncoded
    Call<MResponse> saveAdmod(@Field("id") String id,
                               @Field("deviceId") String deviceId,
                               @Field("created_at") Long createdAt);
}