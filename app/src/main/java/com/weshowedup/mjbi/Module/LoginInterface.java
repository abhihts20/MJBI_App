package com.weshowedup.mjbi.Module;

import com.weshowedup.mjbi.Response.LoginResponse.LoginResponse;
import com.weshowedup.mjbi.Response.RegistrationOTPResponse.RegistrationOTPResponse;
import com.weshowedup.mjbi.Response.RegistrationResponse.RegistrationResponse;

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

    @FormUrlEncoded
    @POST("users/registration")
    Call<RegistrationOTPResponse> reg_otp(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("pincode") String pincode,
            @Field("device_token") String device_token,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/registration")
    Call<RegistrationResponse> registration(
            @Field("user_id") String user_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("address") String address,
            @Field("pincode") String pincode,
            @Field("device_token") String device_token,
            @Field("password") String password,
            @Field("otp") String otp
    );

}
