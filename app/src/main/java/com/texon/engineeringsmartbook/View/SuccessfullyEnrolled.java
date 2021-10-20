package com.texon.engineeringsmartbook.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.texon.engineeringsmartbook.R;

public class SuccessfullyEnrolled extends AppCompatActivity {

    ImageButton btnBackMenu, btnNotificationsMenu, bntHomeMenu, btnProfileMenu, btnScanMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successfully_enrolled);

        btnBackMenu = findViewById(R.id.btnBackMenu);
        btnNotificationsMenu = findViewById(R.id.btnNotificationsMenu);
        bntHomeMenu = findViewById(R.id.btnHomeMenu);
        btnProfileMenu = findViewById(R.id.btnProfileMenu);
        btnScanMenu = findViewById(R.id.btnScannerMenu);



        btnBackMenu.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BookEnrollment.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), BookEnrollment.class);
        startActivity(intent);
        finish();
    }
}

