package com.weshowedup.mjbi.Module;

import com.weshowedup.mjbi.Response.LoginResponse.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {

    String BASE_URL = "http://192.168.43.24/mjbiotechindustries/index.php/api/";

    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> login(
            @Field("username") String username,
            @Field("password") String password,
            @Field("device_token") String device_token
            );



}
