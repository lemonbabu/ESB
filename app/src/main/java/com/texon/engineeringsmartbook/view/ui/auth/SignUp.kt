package com.texon.engineeringsmartbook.view.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.texon.engineeringsmartbook.databinding.ActivitySignUpBinding

class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLoginNav.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnSignUp.setOnClickListener{
            val intent = Intent(applicationContext, OtpVerify::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Closing Activity")
            .setMessage("Are you sure? Do you want to exit this app?")
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int -> finish() }
            .setNegativeButton("No", null)
            .show()
    }
}