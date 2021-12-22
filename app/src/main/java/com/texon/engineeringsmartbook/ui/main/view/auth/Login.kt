package com.texon.engineeringsmartbook.ui.main.view.auth

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.DialogInterface
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.texon.engineeringsmartbook.databinding.ActivityLoginBinding
import com.texon.engineeringsmartbook.data.api.LoginInterface
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.ui.main.view.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.layout_loader.view.*
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.security.AccessController.getContext

@DelicateCoroutinesApi
class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginApi: LoginInterface by lazy { RetrofitClient.getUserLogin() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkSession()
        binding.loader.progressBar.visibility = View.GONE

        @SuppressLint("HardwareIds") val androidID = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        // Toast.makeText(applicationContext, androidID, Toast.LENGTH_LONG).show()

        binding.btnSingUpNav.setOnClickListener {
            binding.btnSingUpNav.isClickable = false
            val intent = Intent(applicationContext, SignUp::class.java)
            startActivity(intent)
            finish()
        }

        binding.txtForgetPassword.setOnClickListener {
            binding.txtForgetPassword.isClickable = false
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

            binding.btnLogin.isClickable = false
            binding.loader.progressBar.visibility = View.VISIBLE
            try {
                login(phone, password, androidID)
            }catch (e: Exception){
                Log.d("Login=", "coroutine scope error")
            }


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




    private fun login(phone: String, password: String, mac: String){
        GlobalScope.launch(Dispatchers.IO){
            try {
                val response = loginApi.userLogin(phone, password, mac)
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        response.body()?.success.let {
                            if(binding.swsRememberMe.isChecked){
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
                            Log.d("Login= ", "Login successfully")
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("nav", "home")
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(applicationContext,"Phone or Password not Match",Toast.LENGTH_SHORT).show()
                        binding.loader.progressBar.visibility = View.GONE
                        binding.btnLogin.isClickable = true
                    }
                    Log.d("Login error= ", response.code().toString() + response.errorBody().toString())
                }
            }catch (e : Exception){
                withContext(Dispatchers.Main){
                    binding.loader.progressBar.visibility = View.GONE
                    binding.btnLogin.isClickable = true
                    Toast.makeText(applicationContext,"Phone or Password not Match",Toast.LENGTH_SHORT).show()
                    Log.d("Login exception= ", e.toString())
                }
            }
        }
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