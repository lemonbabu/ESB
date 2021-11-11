package com.texon.engineeringsmartbook.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.texon.engineeringsmartbook.R;
import com.texon.engineeringsmartbook.view.ui.auth.Login;
import com.texon.engineeringsmartbook.view.ui.auth.UserProfile;

public class BookEnrollment extends AppCompatActivity {

    ImageButton btnBackMenu, btnNotificationsMenu, bntHomeMenu, btnProfileMenu, btnScanMenu;
    Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_enrollment);

        btnBackMenu = findViewById(R.id.btnBackMenu);
        btnNotificationsMenu = findViewById(R.id.btnNotificationsMenu);
        bntHomeMenu = findViewById(R.id.btnHomeMenu);
        btnProfileMenu = findViewById(R.id.btnProfileMenu);
        btnScanMenu = findViewById(R.id.btnScannerMenu);

        btnApply = findViewById(R.id.btnApply);


        btnBackMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        btnApply.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SuccessfullyEnrolled.class);
            startActivity(intent);
            finish();
        });

        btnProfileMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}