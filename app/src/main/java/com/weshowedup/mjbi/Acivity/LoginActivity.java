package com.weshowedup.mjbi.Acivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.weshowedup.mjbi.Module.RetrofitClass;
import com.weshowedup.mjbi.Module.SharedPrefManager;
import com.weshowedup.mjbi.R;
import com.weshowedup.mjbi.Response.LoginResponse.LoginResponse;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText email, password;
    ProgressBar progressBarLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String device_token = FirebaseInstanceId.getInstance().getToken();
    SharedPrefManager sharedPrefManager;
    TextView signup, registration, forgotPassword;
    CheckBox remember;
    Button loginButton;
    private String TAG = "LoginActivity ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        signup = findViewById(R.id.signup_text);
        loginButton = findViewById(R.id.login_button);
        forgotPassword = findViewById(R.id.forgot_password);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        remember = findViewById(R.id.remember_me);
        progressBarLogin=findViewById(R.id.progress_login);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(email.getText()).trim().isEmpty() || !String.valueOf(email.getText()).trim().matches(emailPattern) || String.valueOf(password.getText()).trim().isEmpty()) {
                    loginButton.setVisibility(View.INVISIBLE);
                } else
                    loginButton.setVisibility(View.VISIBLE);
            }
        };
        loginButton.setVisibility(View.INVISIBLE);
        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(String.valueOf(email.getText()).trim(), String.valueOf(password.getText()).trim());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        //Sign Up Intent
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity();
            }
        });
    }

    public void startActivity() {
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        if (Build.VERSION.SDK_INT > 20) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this);
            startActivity(i, options.toBundle());
        } else {
            startActivity(i);
        }
    }

    public void login(String emailL, String passwordL) {
        progressBarLogin.setVisibility(View.VISIBLE);
        try {
            Call<LoginResponse> call = new RetrofitClass().retrofit().login(emailL, passwordL, device_token);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus()) {
                            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                            intent.putExtra("id", response.body().getData().getId());
                            intent.putExtra("name", response.body().getData().getName());
                            intent.putExtra("email", response.body().getData().getEmail());
                            sharedPrefManager.putString(getApplicationContext(), "id", response.body().getData().getId());
                            sharedPrefManager.putString(getApplicationContext(), "device_token", device_token);
                            if (remember.isChecked()){
                                sharedPrefManager.putString(getApplicationContext(),"email",response.body().getData().getEmail());
                                sharedPrefManager.putString(getApplicationContext(),"password",passwordL);
                                sharedPrefManager.putString(getApplicationContext(),"name",response.body().getData().getName());
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            progressBarLogin.setVisibility(View.INVISIBLE);

                            Snackbar.make(getWindow().getDecorView(), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressBarLogin.setVisibility(View.GONE);
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    Snackbar.make(getWindow().getDecorView(), "Connection Fail Try Again", Snackbar.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}