package com.texon.engineeringsmartbook.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.texon.engineeringsmartbook.R;
import com.texon.engineeringsmartbook.View.Auth.Login;

public class SplashScreen extends AppCompatActivity {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

    }
}