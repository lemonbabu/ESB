package com.texon.engineeringsmartbook.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.texon.engineeringsmartbook.R
import android.content.Intent
import com.texon.engineeringsmartbook.view.ui.auth.Login
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        btnNext.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}