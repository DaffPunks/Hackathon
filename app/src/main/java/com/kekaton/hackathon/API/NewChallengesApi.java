package com.kekaton.hackathon.API;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewChallengesApi {
    @FormUrlEncoded
    @POST ("api/test")
    Call<Object> newChallenge(@FieldMap Map<String, String> map);
}
