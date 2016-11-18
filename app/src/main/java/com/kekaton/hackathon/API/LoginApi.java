package com.kekaton.hackathon.API;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


//http://138.68.101.176/api/login
public interface LoginApi {
    @FormUrlEncoded
    @POST ("api/login")
    Call<Object> signup(@FieldMap Map<String, String> map);
}
