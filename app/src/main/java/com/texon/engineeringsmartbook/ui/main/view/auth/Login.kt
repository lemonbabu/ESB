package com.texon.engineeringsmartbook.ui.main.view.auth

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.DialogInterface
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.texon.engineeringsmartbook.databinding.ActivityLoginBinding
import com.texon.engineeringsmartbook.data.api.LoginInterface
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.LoginResponse
import com.texon.engineeringsmartbook.ui.main.view.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_loader.view.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginApi: LoginInterface by lazy { RetrofitClient.getUserLogin() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loader.layoutLoader.visibility = View.GONE
        checkSession()

        binding.btnSingUpNav.setOnClickListener {
            val intent = Intent(applicationContext, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        binding.txtForgetPassword.setOnClickListener {
            val intent = Intent(applicationContext, ForgotPassword::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener{

            val phone = binding.txtUserPhone.text.toString().trim()
            val password = binding.txtUserPassword.text.toString().trim()

            if(phone.isEmpty()){
                //Toast.makeText(applicationContext, "Email required", Toast.LENGTH_SHORT).show()
                binding.txtUserPhone.error = "Email required"
                binding.txtUserPhone.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                //Toast.makeText(applicationContext, "Password required", Toast.LENGTH_SHORT).show()
                binding.txtUserPassword.error = "Password required"
                binding.txtUserPassword.requestFocus()
                return@setOnClickListener
            }

            loader.progressBar.visibility = View.VISIBLE
            binding.btnLogin.isClickable = false
            login(phone, password)
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


    private fun login(phone: String, password: String){
        loginApi.userLogin(phone, password, "DeviceName")
            .enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.body()?.success!!){
                        if(swsRememberMe.isChecked){
                            val sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.apply{
                                putBoolean("session", true)
                                putBoolean("remember", true)
                                putString("token", response.body()?.data?.token)
                                putString("name", response.body()?.data?.user?.name)
                                putString("phone", response.body()?.data?.user?.phone)
                                putString("email", response.body()?.data?.user?.email)
                                putString("avatar", response.body()?.data?.user?.avatarUrl)
                            }.apply()
                        }else{
                            val sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.apply{
                                putBoolean("session", true)
                                putBoolean("remember", false)
                                putString("token", response.body()?.data?.token)
                                putString("name", response.body()?.data?.user?.name)
                                putString("phone", response.body()?.data?.user?.phone)
                                putString("email", response.body()?.data?.user?.email)
                                putString("avatar", response.body()?.data?.user?.avatarUrl)
                            }.apply()
                        }
                        Toast.makeText(applicationContext, "Login Successfully", Toast.LENGTH_SHORT).show()
                        binding.loader.layoutLoader.visibility = View.GONE
                        binding.btnLogin.isClickable = true
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        intent.putExtra("nav", "home")
                        startActivity(intent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext,"Phone or Password not Match",Toast.LENGTH_SHORT).show()
                    binding.loader.layoutLoader.visibility = View.GONE
                    binding.btnLogin.isClickable = true
                }
            })
    }


    private fun checkSession(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
        val remember = sharedPreferences.getBoolean("remember", false)
        if(remember){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("nav", "home")
            startActivity(intent)
            finish()
        }
    }


}