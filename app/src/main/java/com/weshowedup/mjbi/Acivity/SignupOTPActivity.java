package com.weshowedup.mjbi.Acivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.Module.SharedPrefManager;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.RegistrationOTPResponse.RegistrationOTPResponse;
import com.weshowedup.mjbi.Response.RegistrationResponse.RegistrationResponse;
import com.weshowedup.mjbi.databinding.ActivitySignupOtpBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupOTPActivity extends AppCompatActivity {
    ActivitySignupOtpBinding binding;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup_otp);
        binding.otpEmail.setText(getIntent().getStringExtra("email"));
        try {
            binding.resendOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.otpProgressbar.setVisibility(View.VISIBLE);
                    Call<RegistrationOTPResponse> call = new RetrofitClass().retrofit()
                            .reg_otp(getIntent().getStringExtra("id"), getIntent().getStringExtra("name"),
                                    getIntent().getStringExtra("email"), getIntent().getStringExtra("mobile"),
                                    getIntent().getStringExtra("address"), getIntent().getStringExtra("pincode"), getIntent().getStringExtra("device_token"),
                                    getIntent().getStringExtra("password"));
                    call.enqueue(new Callback<RegistrationOTPResponse>() {
                        @Override
                        public void onResponse(Call<RegistrationOTPResponse> call, Response<RegistrationOTPResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus()) {
                                    binding.otpProgressbar.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(SignupOTPActivity.this, SignupOTPActivity.class);
                                    intent.putExtra("id", getIntent().getStringExtra("id"));
                                    intent.putExtra("name", getIntent().getStringExtra("name"));
                                    intent.putExtra("email", getIntent().getStringExtra("email"));
                                    intent.putExtra("mobile", getIntent().getStringExtra("mobile"));
                                    intent.putExtra("address", getIntent().getStringExtra("address"));
                                    intent.putExtra("pincode", getIntent().getStringExtra("pincode"));
                                    intent.putExtra("device_token", getIntent().getStringExtra("device_token"));
                                    intent.putExtra("password", getIntent().getStringExtra("password"));
                                    intent.putExtra("otp", String.valueOf(response.body().getData().getOtp()).trim());
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<RegistrationOTPResponse> call, Throwable t) {
                            binding.otpProgressbar.setVisibility(View.INVISIBLE);
                            Snackbar.make(getWindow().getDecorView(), "Try Again", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            binding.submitOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.otpProgressbar.setVisibility(View.VISIBLE);
                    String otp1 = getIntent().getStringExtra("otp");
                    binding.resendOtp.getText();
                    if (otp1 != null) {
                        if (otp1.equals(String.valueOf(binding.otpEdit.getText()).trim())) {
                            Call<RegistrationResponse> call = new RetrofitClass().retrofit()
                                    .registration(
                                            "1", getIntent().getStringExtra("name"),
                                            getIntent().getStringExtra("email"),
                                            getIntent().getStringExtra("mobile"),
                                            getIntent().getStringExtra("address"),
                                            getIntent().getStringExtra("pincode"),
                                            getIntent().getStringExtra("device_token"),
                                            getIntent().getStringExtra("password"), otp1);
                            call.enqueue(new Callback<RegistrationResponse>() {
                                @Override
                                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                                    binding.otpProgressbar.setVisibility(View.INVISIBLE);
                                    if (response.body() != null) {
                                        if (response.body().getStatus()) {
                                            SharedPrefManager sharedPrefManager = new SharedPrefManager(getApplication().getBaseContext());
                                            Intent intent = new Intent(SignupOTPActivity.this, MainActivity.class);
                                            intent.putExtra("id", String.valueOf(response.body().getData().getId()));
                                            sharedPrefManager.putString(getApplication().getBaseContext(), "id", String.valueOf(response.body().getData().getId()));
                                            sharedPrefManager.putString(getApplication().getBaseContext(), "device_token", getIntent().getStringExtra("device_token"));
                                            intent.putExtra("name", response.body().getData().getName());
                                            intent.putExtra("email", response.body().getData().getEmail());
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        binding.otpProgressbar.setVisibility(View.INVISIBLE);
                                        Snackbar.make(getWindow().getDecorView(), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                                    binding.otpProgressbar.setVisibility(View.INVISIBLE);
                                    Log.d("error", "onFailure: " + t.getMessage());
                                    Snackbar.make(getWindow().getDecorView(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            binding.otpProgressbar.setVisibility(View.INVISIBLE);
                            Snackbar.make(getWindow().getDecorView(),"Invalid OTP",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}