package com.weshowedup.mjbi.Acivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.RegistrationOTPResponse.RegistrationOTPResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    TextInputEditText name, email, phone, password, confirmPassword, address, pincode;
    Button signupButton;
    ProgressBar singupProgress;
    String device_token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimation();
        setContentView(R.layout.activity_signup);
        Objects.requireNonNull(getSupportActionBar()).hide();
        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        phone = findViewById(R.id.signup_phone);
        address = findViewById(R.id.signup_address);
        pincode = findViewById(R.id.signup_pincode);
        password = findViewById(R.id.signup_pass);
        confirmPassword = findViewById(R.id.signup_confirmpass);
        signupButton = findViewById(R.id.signup_button);
        singupProgress = findViewById(R.id.progress_signup);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(name.getText()).isEmpty() || String.valueOf(email.getText()).isEmpty() || String.valueOf(phone.getText()).isEmpty()
                        || String.valueOf(address.getText()).isEmpty() || String.valueOf(pincode.getText()).isEmpty() || String.valueOf(password.getText()).isEmpty()
                        || String.valueOf(confirmPassword.getText()).isEmpty() || String.valueOf(phone.getText()).length() != 10) {
                    signupButton.setEnabled(false);
                } else {
                    signupButton.setEnabled(true);
                }
            }
        };

        name.addTextChangedListener(textWatcher);
        email.addTextChangedListener(textWatcher);
        phone.addTextChangedListener(textWatcher);
        address.addTextChangedListener(textWatcher);
        pincode.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        confirmPassword.addTextChangedListener(textWatcher);
        signupButton.setEnabled(false);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singupProgress.setVisibility(View.VISIBLE);
                signupButton.setEnabled(false);
                if (String.valueOf(password.getText()).trim().equals(String.valueOf(confirmPassword.getText()).trim())) {
                    if (String.valueOf(password.getText()).length() >= 6) {

                    } else {
                        singupProgress.setVisibility(View.INVISIBLE);
                        signupButton.setEnabled(true);
                        Snackbar.make(getWindow().getDecorView(), "Password must be greater than 6", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    singupProgress.setVisibility(View.INVISIBLE);
                    signupButton.setEnabled(true);
                    Snackbar.make(getWindow().getDecorView(), "Password does not match", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void registration(final String name, final String email, final String phone, final String password, final String address, final String pincode) {
        final String user_id = "1";
        try {
            Call<RegistrationOTPResponse> call = new RetrofitClass().retrofit().reg_otp(
                    user_id, name, email, phone, address, pincode, device_token, password
            );
            call.enqueue(new Callback<RegistrationOTPResponse>() {
                @Override
                public void onResponse(Call<RegistrationOTPResponse> call, Response<RegistrationOTPResponse> response) {
                    if (response.body() != null) {
                        singupProgress.setVisibility(View.INVISIBLE);
                        signupButton.setEnabled(true);
                        if (response.body().getStatus()) {
                            Intent intent = new Intent(SignupActivity.this, SignupOTPActivity.class);
                            intent.putExtra("otp", String.valueOf(response.body().getData().getOtp()).trim());
                            intent.putExtra("id", "1");
                            intent.putExtra("name", name);
                            intent.putExtra("email", email);
                            intent.putExtra("mobile", phone);
                            intent.putExtra("address", address);
                            intent.putExtra("pincode", pincode);
                            intent.putExtra("device_token", device_token);
                            intent.putExtra("password", password);
                            startActivity(intent);
                            finish();
                        } else {
                            singupProgress.setVisibility(View.INVISIBLE);
                            signupButton.setEnabled(false);
                            Snackbar.make(getWindow().getDecorView(), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    } else {
                        singupProgress.setVisibility(View.INVISIBLE);
                        signupButton.setEnabled(true);
                        Snackbar.make(getWindow().getDecorView(), "Email Already Exists", Snackbar.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationOTPResponse> call, Throwable t) {
                    singupProgress.setVisibility(View.INVISIBLE);
                    signupButton.setEnabled(true);
                    Log.d("error","OnFailure"+t.getMessage());
                    if (t.getMessage() != null)
                        if (t.getMessage().equals("java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 2 column 49 path $.data")) {
                            Snackbar.make(getWindow().getDecorView(), "User Exist", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(getWindow().getDecorView(), "Try Again", Snackbar.LENGTH_SHORT).show();
                        }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(500);
            slide.setInterpolator(new DecelerateInterpolator());
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }
}