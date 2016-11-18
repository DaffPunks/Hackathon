package com.kekaton.hackathon.API;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by User on 18.11.2016.
 */
//http://138.68.101.176/api/signup
public interface RegisterApi {
    @FormUrlEncoded
    @POST ("api/signup")
    Call<Object> signup(@FieldMap Map<String, String> map);
}
