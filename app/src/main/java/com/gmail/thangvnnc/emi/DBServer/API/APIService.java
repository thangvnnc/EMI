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
                                @Field("content") String body,
                                @Field("created_at") Long userId);
}