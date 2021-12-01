package com.texon.engineeringsmartbook.ui.main.view.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.ChangePasswordResponse
import com.texon.engineeringsmartbook.data.model.SendOtpResponses
import com.texon.engineeringsmartbook.databinding.ActivityChangePasswordBinding
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_loader.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class ChangePassword : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private val changPassword: ApiInterfaces.ChangePasswordInterface by lazy { RetrofitClient.getChangePassword() }
    private var phone = ""
    private var token = ""
    private var pass = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phone = intent.getStringExtra("phone").toString()
        token = intent.getStringExtra("token").toString()

        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            pass = binding.txtPassword.text.toString().trim()
            val conPass = binding.txtConPassword.text.toString().trim()

            if(pass.isEmpty()){
                binding.txtPassword.error = "Password must be needed!!"
                binding.txtPassword.requestFocus()
                return@setOnClickListener
            }

            if(pass != conPass){
                binding.txtConPassword.error = "Password is not match?"
                binding.txtConPassword.requestFocus()
                return@setOnClickListener
            }
            binding.btnConfirm.isClickable = false
            changePass()
        }

    }

    private fun changePass(){
        changPassword.changePass(phone, token, pass, pass)
            .enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    if (response.body()?.success!!){
                        Toast.makeText(applicationContext,"Password Change Successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,"Something Wrong!!", Toast.LENGTH_SHORT).show()
                    loader.progressBar.visibility = View.INVISIBLE
                }
            })
        binding.btnConfirm.isClickable = true
    }
}