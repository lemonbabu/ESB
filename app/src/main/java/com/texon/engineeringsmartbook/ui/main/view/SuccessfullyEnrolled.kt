package com.texon.engineeringsmartbook.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.WindowManager
import com.texon.engineeringsmartbook.databinding.ActivitySuccessfullyEnrolledBinding
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class SuccessfullyEnrolled : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessfullyEnrolledBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessfullyEnrolledBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.titleBar.btnBackMenu.setOnClickListener{
            val intent = Intent(applicationContext, BookEnrollment::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, BookEnrollment::class.java)
        startActivity(intent)
        finish()
    }
}