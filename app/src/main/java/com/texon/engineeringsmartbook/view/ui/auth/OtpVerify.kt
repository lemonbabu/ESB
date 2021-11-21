package com.texon.engineeringsmartbook.view.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import com.texon.engineeringsmartbook.databinding.ActivityOtpVerifyBinding

class OtpVerify : AppCompatActivity() {
    private lateinit var binding: ActivityOtpVerifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        val intent = Intent(applicationContext, SignUp::class.java)
        startActivity(intent)
        finish()
    }
}