package com.texon.engineeringsmartbook.View.Auth;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.texon.engineeringsmartbook.R;
import com.texon.engineeringsmartbook.View.Auth.Login;

public class SignUp extends AppCompatActivity {

    Button btnSignUp, btnLoginNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnLoginNav = findViewById(R.id.btnLoginNav);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLoginNav.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OtpVerify.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure? Do you want to exit this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}