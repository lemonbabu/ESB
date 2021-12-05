package com.texon.engineeringsmartbook.ui.main.view.activities

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.texon.engineeringsmartbook.BuildConfig
import com.texon.engineeringsmartbook.databinding.ActivitySplashScreenBinding
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private var versionName: String = BuildConfig.VERSION_NAME
    private val appVersion: ApiInterfaces.AppVersionInterface by lazy { RetrofitClient.getAppVersion() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loader.loaderSplash.visibility = View.VISIBLE
        //checkVersion()


        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = appVersion.getVersion().awaitResponse()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        if (response.body()?.data?.app_version != versionName) {
                            AlertDialog.Builder(this@SplashScreen)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Update Required")
                                .setMessage("Update your apps form play store")
                                .setPositiveButton("Update") { _, _ ->
                                    val uri = Uri.parse("https://engineeringsmartbook.com/assets/download/ESB%20v1.0.0.apk")
                                    //val uri = Uri.parse("https://play.google.com/store/apps/details?id=null")
                                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                                    finish()
                                }
                                .setNegativeButton("Close", null)
                                .show()
                        }else{
                            loadData()
                        }
                    }
                }
            } catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "Check you Internet Connection", Toast.LENGTH_LONG).show()
                }
             }

        }


        //Toast.makeText(applicationContext, versionName, Toast.LENGTH_SHORT).show()

        binding.btnNext.setOnClickListener{
            saveData()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("SplashScreen", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply{
            putBoolean("view", true)
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("SplashScreen", Context.MODE_PRIVATE)
        val session = sharedPreferences.getBoolean("view", false)
        if(session){
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }else{
            binding.loader.loaderSplash.visibility = View.GONE
        }
    }

}