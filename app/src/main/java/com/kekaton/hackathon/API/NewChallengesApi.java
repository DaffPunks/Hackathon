package com.kekaton.hackathon.API;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewChallengesApi {
    @FormUrlEncoded
    @POST ("api/challenge/create")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Object> newChallenge(@Field("token=")String string);
}
//@Body Map<String, String> map, @Header("token") String token