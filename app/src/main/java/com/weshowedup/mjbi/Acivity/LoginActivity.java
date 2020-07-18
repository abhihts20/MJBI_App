package com.weshowedup.mjbi.Acivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        progressBarLogin = findViewById(R.id.progress_login);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (String.valueOf(email.getText()).trim().isEmpty() || String.valueOf(password).trim().isEmpty() || !String.valueOf(email.getText()).trim().matches(emailPattern)) {
                    loginButton.setEnabled(false);
                } else {
                    loginButton.setEnabled(true);
                }
            }
        };
        sharedPrefManager = new SharedPrefManager(this);
        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        loginButton.setEnabled(false);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarLogin.setVisibility(View.VISIBLE);
                loginButton.setEnabled(false);
                login(String.valueOf(email.getText()).trim(),String.valueOf(password.getText()).trim());
            }
        });
    }

    public void login(String usernameL, String passwordL) {
        Call<LoginResponse> call = new RetrofitClass().retrofit().login(usernameL, passwordL, device_token);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressBarLogin.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("name", response.body().getData().getName());
                        intent.putExtra("email", response.body().getData().getEmail());
                        intent.putExtra("id", response.body().getData().getId());
                        sharedPrefManager.putString(getApplicationContext(), "id", response.body().getData().getId());
                        sharedPrefManager.putString(getApplicationContext(), "device_token", device_token);
                        if (remember.isChecked()) {
                            sharedPrefManager.putString(getApplicationContext(), "email", response.body().getData().getEmail());
                            sharedPrefManager.putString(getApplicationContext(), "password", passwordL);
                            sharedPrefManager.putString(getApplicationContext(), "name", response.body().getData().getName());
                        }
                        startActivity(intent);
                        finish();
                    }
                    else{
                        progressBarLogin.setVisibility(View.INVISIBLE);
                        loginButton.setEnabled(false);
                        Snackbar.make(getWindow().getDecorView(),response.body().getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    progressBarLogin.setVisibility(View.INVISIBLE);
                    loginButton.setEnabled(true);
                    Snackbar.make(getWindow().getDecorView(), "Email does not Exists", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressBarLogin.setVisibility(View.INVISIBLE);
                loginButton.setEnabled(true);
                Log.d("error", "onFailure: " + t.getMessage());
                Snackbar.make(getWindow().getDecorView(),"Try Again",Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}