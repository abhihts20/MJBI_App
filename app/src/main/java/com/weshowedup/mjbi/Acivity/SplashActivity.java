package com.weshowedup.mjbi.Acivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.weshowedup.mjbi.R;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    int TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Objects.requireNonNull(getSupportActionBar()).hide();
        ImageView logo = findViewById(R.id.splash_logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME);

        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(myAnim);
    }
}