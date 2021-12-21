package com.texon.engineeringsmartbook.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.texon.engineeringsmartbook.databinding.ActivityBookEnrollmentBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class BookEnrollment : AppCompatActivity() {

    private lateinit var binding: ActivityBookEnrollmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookEnrollmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding.titleBar.btnBackMenu.setOnClickListener{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnApply.setOnClickListener {
            val intent = Intent(applicationContext, SuccessfullyEnrolled::class.java)
            startActivity(intent)
            finish()
        }

        binding.appBar.btnProfileMenu.setOnClickListener {  }

    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
        finish()
    }
}