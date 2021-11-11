package com.texon.engineeringsmartbook.view.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.texon.engineeringsmartbook.Dashboard;
import com.texon.engineeringsmartbook.R;

public class UserProfile extends AppCompatActivity {

    Button btnLogOut, btnEditProfile;
    ImageButton btnHome, btnProfile, btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnEditProfile = findViewById(R.id.btnEditProfile);

        btnHome = findViewById(R.id.btnHomeMenu);
        btnProfile = findViewById(R.id.btnProfileMenu);
        btnProfile.setImageResource(R.drawable.ic_user_profile_primary_color);
        btnScan = findViewById(R.id.btnScannerMenu);


        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
            startActivity(intent);
            finish();
        });

        btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
        finish();
    }
}