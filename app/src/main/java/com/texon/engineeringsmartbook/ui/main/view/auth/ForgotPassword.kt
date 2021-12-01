package com.texon.engineeringsmartbook.ui.main.view.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.SendOtpResponses
import com.texon.engineeringsmartbook.databinding.ActivityFogotPasswordBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_loader.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class ForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityFogotPasswordBinding
    private var phone: String = ""
    private val sendOtp: ApiInterfaces.ForgotPasswordInterface by lazy { RetrofitClient.getForgotOtp() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFogotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSendOtp.setOnClickListener {
            phone = binding.txtUserPhone.text.toString().trim()
            if(phone.isEmpty()){
                binding.txtUserPhone.error = "Phone must be needed!!"
                binding.txtUserPhone.requestFocus()
                return@setOnClickListener
            }
            binding.btnSendOtp.isClickable = false
            sendOtp()
        }


    }


    override fun onBackPressed() {
        val intent = Intent(applicationContext, ForgotPassword::class.java)
        startActivity(intent)
        finish()
    }

    private fun sendOtp(){
        sendOtp.sendOtp(phone)
            .enqueue(object : Callback<SendOtpResponses> {
                override fun onResponse(
                    call: Call<SendOtpResponses>,
                    response: Response<SendOtpResponses>
                ) {
                    if (response.body()?.success!!){
                        Toast.makeText(applicationContext,"Otp Send Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, OtpVerify::class.java)
                        intent.putExtra("tag", phone)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<SendOtpResponses>, t: Throwable) {
                    Toast.makeText(applicationContext,"Phone or Password not Match", Toast.LENGTH_SHORT).show()
                    loader.progressBar.visibility = View.INVISIBLE
                }
            })
        binding.btnSendOtp.isClickable = true
    }
    
}