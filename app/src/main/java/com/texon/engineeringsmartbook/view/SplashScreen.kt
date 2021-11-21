package com.texon.engineeringsmartbook.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.texon.engineeringsmartbook.R
import android.content.Intent
import android.content.SharedPreferences
import com.texon.engineeringsmartbook.view.ui.auth.Login
import kotlinx.android.synthetic.main.activity_splash_screen.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (loadData()){
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        btnNext.setOnClickListener{
            saveData()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("SplashScreen", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("view", true)
        }.apply()
    }

    private fun loadData(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("SplashScreen", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("view", false)
    }
}