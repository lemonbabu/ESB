package com.texon.engineeringsmartbook.ui.main.view.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.texon.engineeringsmartbook.databinding.ActivityOtpVerifyBinding
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.SendOtpResponses
import com.texon.engineeringsmartbook.data.model.UserRegResponses
import com.texon.engineeringsmartbook.data.model.VerifyOtpResponses
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class OtpVerify : AppCompatActivity() {

    private lateinit var binding: ActivityOtpVerifyBinding
    private val sendOtp: ApiInterfaces.SendOtpInterface by lazy { RetrofitClient.getSendOtp() }
    private val verifyOtp: ApiInterfaces.OtpVerifyInterface by lazy { RetrofitClient.getOtpVerify() }
    private val userReg: ApiInterfaces.UserRegistrationInterface by lazy { RetrofitClient.getUserReg() }
    private var name = ""
    private var email = ""
    private var phone = ""
    private var pass = ""
    private var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This flag set for forgot password use in same otp checking system
        val flg = intent.getStringExtra("tag")
        binding = ActivityOtpVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(flg == "signUp"){
            getData()
        } else{
            phone = flg.toString()
        }


        val countDownTimer = object : CountDownTimer(60000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.txtTimer.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                binding.btnSendOtpAgain.visibility = View.VISIBLE
                binding.pnlTimer.visibility = View.INVISIBLE
            }
        }.start()

//        GlobalScope.launch(Dispatchers.IO) {
//
//            withContext(Dispatchers.Main){
//
//            }
//        }

        binding.btnConfirmCode.setOnClickListener {
            code = binding.txtOtpCode.text.toString().trim()
            if(code.isEmpty()){
                binding.txtOtpCode.error = "Code is required"
                binding.txtOtpCode.requestFocus()
                return@setOnClickListener
            }
            binding.loader.progressBar.visibility = View.VISIBLE
            binding.btnConfirmCode.isClickable = false

            if(flg == "signUp"){
                confirmOtp()
            } else{
                confirmOtpForgotPassword()
            }

        }

        binding.btnSendOtpAgain.setOnClickListener {
            binding.btnSendOtpAgain.visibility = View.GONE
            binding.pnlTimer.visibility = View.VISIBLE
            countDownTimer.start()
            sendOtp()
            binding.btnConfirmCode.isClickable = true
        }

    }



    override fun onBackPressed() {
        val intent = Intent(applicationContext, SignUp::class.java)
        startActivity(intent)
        finish()
    }

    private fun getData(){
        val sharedPreferences = getSharedPreferences("SignUp", Context.MODE_PRIVATE)
        val session = sharedPreferences.getBoolean("session", false)
        if(session){
            phone = sharedPreferences.getString("phone", "").toString()
            binding.txtPhoneNumber.text = phone
            name = sharedPreferences.getString("name", "").toString()
            email = sharedPreferences.getString("email", "").toString()
            pass = sharedPreferences.getString("pass", "").toString()
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
                        Toast.makeText(applicationContext,"Otp Send Successfully", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SendOtpResponses>, t: Throwable) {
                    Toast.makeText(applicationContext,"OTP is not send Error", Toast.LENGTH_SHORT).show()
                }
            })
    }


    private fun confirmOtp(){
        verifyOtp.verifyOtp(phone, code)
            .enqueue(object : Callback<VerifyOtpResponses> {
                override fun onResponse(
                    call: Call<VerifyOtpResponses>,
                    response: Response<VerifyOtpResponses>
                ) {
                    if (response.body()?.success!!){
                        Toast.makeText(applicationContext,"Otp Verified Successfully", Toast.LENGTH_SHORT).show()
                        response.body()?.data?.token?.let { registration(it) }
                    }
                }

                override fun onFailure(call: Call<VerifyOtpResponses>, t: Throwable) {
                    Toast.makeText(applicationContext,"OTP is not valid", Toast.LENGTH_SHORT).show()
                    binding.loader.progressBar.visibility = View.GONE
                }
            })
    }

    private fun registration(token: String){
        userReg.userReg(phone, token, pass, pass, name, email, "Phone_name")
            .enqueue(object : Callback<UserRegResponses> {
                override fun onResponse(
                    call: Call<UserRegResponses>,
                    response: Response<UserRegResponses>
                ) {
                    if (response.body()?.success!!){
                        Toast.makeText(applicationContext,"Registration Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<UserRegResponses>, t: Throwable) {
                    Toast.makeText(applicationContext,"This user is already registered", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, Login::class.java)
                    startActivity(intent)
                    finish()
                }
            })
    }


    private fun confirmOtpForgotPassword(){
        verifyOtp.verifyOtp(phone, code)
            .enqueue(object : Callback<VerifyOtpResponses> {
                override fun onResponse(
                    call: Call<VerifyOtpResponses>,
                    response: Response<VerifyOtpResponses>
                ) {
                    if (response.body()?.success!!){
                        Toast.makeText(applicationContext,"Otp Verified Successfully", Toast.LENGTH_SHORT).show()
                        response.body()?.data?.token?.let {
                            val intent = Intent(applicationContext, ChangePassword::class.java)
                            intent.putExtra("phone", phone)
                            intent.putExtra("token", it)
                            startActivity(intent)
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<VerifyOtpResponses>, t: Throwable) {
                    Toast.makeText(applicationContext,"OTP is not valid", Toast.LENGTH_SHORT).show()
                }
            })
    }


}