package com.texon.engineeringsmartbook.ui.main.view.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.texon.engineeringsmartbook.databinding.ActivitySignUpBinding
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.SendOtpResponses
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_loader.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class SignUp : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val sendOtp: ApiInterfaces.SendOtpInterface by lazy { RetrofitClient.getSendOtp() }

    private var name = ""
    private var email = ""
    private var phone = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loader.progressBar.visibility = View.INVISIBLE
        binding.btnSignUp.isEnabled = true
        getData()


        binding.btnLoginNav.setOnClickListener {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignUp.setOnClickListener{

            name = binding.txtFullName.text.toString().trim()
            email = binding.txtEmail.text.toString().trim()
            phone = binding.txtPhone.text.toString().trim()
            pass = binding.txtPass.text.toString().trim()
            //Toast.makeText(applicationContext, name + email + phone + pass, Toast.LENGTH_SHORT).show()

            if(name.isEmpty()){
                binding.txtFullName.error = "Full Name"
                binding.txtFullName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()){
                binding.txtEmail.error = "Email"
                binding.txtEmail.requestFocus()
                return@setOnClickListener
            }

            if (phone.isEmpty()){
                binding.txtPhone.error = "Phone Number"
                binding.txtPhone.requestFocus()
                return@setOnClickListener
            }

            if (phone.length < 11){
                binding.txtPhone.error = "Phone Number is not valid"
                binding.txtPhone.requestFocus()
                return@setOnClickListener
            }

            if (pass.isEmpty()){
                binding.txtPass.error = "Password required"
                binding.txtPass.requestFocus()
                return@setOnClickListener
            }

            if (pass.length < 6){
                binding.txtPass.error = "The password must be at least 6 characters"
                binding.txtPass.requestFocus()
                return@setOnClickListener
            }


            if (pass != binding.txtConPass.text.toString().trim()){
                binding.txtConPass.error = "Password & Confirm Password is not Match"
                binding.txtConPass.requestFocus()
                return@setOnClickListener
            }

            binding.loader.progressBar.visibility = View.VISIBLE
            binding.btnSignUp.isEnabled = false


            setData()
            sendOtp()
        }
    }

    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences("SignUp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("session", true)
        }.apply()

        val intent = Intent(applicationContext, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun setData(){
        val sharedPreferences = getSharedPreferences("SignUp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("session", true)
            putString("name", name)
            putString("email", email)
            putString("phone", phone)
            putString("pass", pass)
        }.apply()
    }

    private fun getData(){
        val sharedPreferences = getSharedPreferences("SignUp", Context.MODE_PRIVATE)
        val session = sharedPreferences.getBoolean("session", false)
        if(session){
            binding.txtFullName.setText(sharedPreferences.getString("name", ""))
            binding.txtEmail.setText(sharedPreferences.getString("email", ""))
            binding.txtPhone.setText(sharedPreferences.getString("phone", ""))
            binding.txtPass.setText(sharedPreferences.getString("pass", ""))
        }
    }


    private fun sendOtp(){
        sendOtp.sendOtp(phone)
            .enqueue(object : Callback<SendOtpResponses> {
                override fun onResponse(
                    call: Call<SendOtpResponses>,
                    response: Response<SendOtpResponses>
                ) {
                    if (response.body()?.success!!){
                        Toast.makeText(applicationContext,"Otp Send Successfully",Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, OtpVerify::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<SendOtpResponses>, t: Throwable) {
                    Toast.makeText(applicationContext,"Phone or Password not Match",Toast.LENGTH_SHORT).show()
                    loader.progressBar.visibility = View.INVISIBLE
                    binding.btnSignUp.isEnabled = true
                }
            })
    }

}