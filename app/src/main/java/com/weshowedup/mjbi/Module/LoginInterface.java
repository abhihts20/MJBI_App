package com.weshowedup.mjbi.Module;

import com.google.gson.JsonObject;
import com.weshowedup.mjbi.Response.AddCartResponse.AddCartResponse;
import com.weshowedup.mjbi.Response.ChangePasswordResponse.ChangePasswordResponse;
import com.weshowedup.mjbi.Response.FeedbackResponse.FeedbackResponse;
import com.weshowedup.mjbi.Response.ForgetOTPResponse.ForgetOTPResponse;
import com.weshowedup.mjbi.Response.ForgotPasswordResponse.ForgotPasswordResponse;
import com.weshowedup.mjbi.Response.LoginResponse.LoginResponse;
import com.weshowedup.mjbi.Response.ProfileResponse.ProfileResponse;
import com.weshowedup.mjbi.Response.RegistrationOTPResponse.RegistrationOTPResponse;
import com.weshowedup.mjbi.Response.RegistrationResponse.RegistrationResponse;
import com.weshowedup.mjbi.Response.RemoveCartResponse.RemoveCartResponse;

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
    @FormUrlEncoded
    @POST("users/forget_password")
    Call<ForgetOTPResponse> otp(
            @Field("username") String username
    );

    @FormUrlEncoded
    @POST("users/forget_password")
    Call<ForgotPasswordResponse> forgotpassword(
            @Field("username") String username,
            @Field("otp") String otp,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("users/change_password")
    Call<ChangePasswordResponse> changepassword(
            @Field("user_id") String id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("home/get_profile")
    Call<ProfileResponse> profile(
            @Field("user_id") String user_id,
            @Field("page") String page
    );

    @FormUrlEncoded
    @POST("home/get_category")
    Call<JsonObject> category(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("home/get_materials_list")
    Call<JsonObject> material(
            @Field("user_id") String user_id,
            @Field("category_id") String category_id,
            @Field("page") int page
    );
    @FormUrlEncoded
    @POST("home/myorder")
    Call<JsonObject> myorder(
            @Field("user_id") String user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("home/today_order")
    Call<JsonObject> todayorder(
            @Field("user_id") String user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("home/add_to_cart")
    Call<JsonObject> cartlist(
            @Field("user_id") String user_id,
            @Field("page") int page
    );

    @FormUrlEncoded
    @POST("home/cart")
    Call<AddCartResponse> cart(
            @Field("cart_id") String cart_id,
            @Field("user_id") String user_id,
            @Field("material_id") String material_id,
            @Field("quantity") int quantity,
            @Field("transaction") String transaction
    );
    @FormUrlEncoded
    @POST("home/feedback")
    Call<FeedbackResponse> feedback(
            @Field("user_id") String user_id,
            @Field("feedback") String feedback
    );
    @FormUrlEncoded
    @POST("home/remove_cart")
    Call<RemoveCartResponse> remove(
            @Field("user_id") String user_id,
            @Field("cart_id") String cart_id
    );

}
