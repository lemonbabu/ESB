package com.texon.engineeringsmartbook.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.texon.engineeringsmartbook.R;
import com.texon.engineeringsmartbook.View.Auth.Login;

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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}