package com.kekaton.hackathon.API;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BibaRetrofit {
    @FormUrlEncoded
    @POST("users.get?")
    Call<Object> translate(@FieldMap Map<String, String> map);
}
