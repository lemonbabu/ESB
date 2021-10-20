package com.texon.engineeringsmartbook.View.Auth;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.texon.engineeringsmartbook.R;
import com.texon.engineeringsmartbook.View.BookEnrollment;

public class Login extends AppCompatActivity {

    Button btnLogin, btnSignUpNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUpNav = findViewById(R.id.btnSingUpNav);

        btnSignUpNav.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
        });

        btnLogin.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), BookEnrollment.class);
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
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", null)
                .show();
    }
}