package com.texon.engineeringsmartbook.ui.main.view

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.data.model.APiBookAccessResponses
import com.texon.engineeringsmartbook.ui.main.view.activities.VideoPlayer
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

@DelicateCoroutinesApi
class Scanner : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private val bookAccess: ApiInterfaces.BookAccessInterface by lazy { RetrofitClient.getBookAccessByQRCode() }
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        val sharedPreferences: SharedPreferences = getSharedPreferences("Session", MODE_PRIVATE)
        val session = sharedPreferences.getBoolean("session", false)
        if(!session){
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }else{
            token = sharedPreferences.getString("token", "")!!
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }

    }

    private fun startScanning() {
        val scannerView: CodeScannerView = findViewById(R.id.qrCodeScanner)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        try {
            codeScanner.decodeCallback = DecodeCallback {
                runOnUiThread {
                    getBookAccess(it.text)
                    Log.d("Scanning", it.text)
                }
            }

            codeScanner.errorCallback = ErrorCallback {
                runOnUiThread {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                }
            }

            scannerView.setOnClickListener { codeScanner.startPreview() }
        }catch (e: Exception){
            Log.d("Scanning", "Exception Error")
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 123){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_SHORT).show()
                startScanning()
            } else{
                Toast.makeText(this, "Camera Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::codeScanner.isInitialized){
            codeScanner.startPreview()
        }
    }

    override fun onPause() {
        if(::codeScanner.isInitialized){
            codeScanner.releaseResources()
        }
        super.onPause()
    }

    private fun getBookAccess(qrCode: String){
        try {
            bookAccess.getBook( qrCode, "Bearer $token")
                .enqueue(object : Callback<APiBookAccessResponses> {
                    override fun onResponse(
                        call: Call<APiBookAccessResponses>,
                        response: Response<APiBookAccessResponses>
                    ) {
                        try{
                            Toast.makeText(applicationContext, response.body()?.data?.video_link, Toast.LENGTH_SHORT).show()
                            response.body()?.data?.video_link?.let {
                                val intent = Intent(applicationContext, VideoPlayer::class.java)
                                intent.putExtra("link", it)
                                startActivity(intent)
                                finish()
                            }
                        }catch (e: Exception){
                            Toast.makeText(applicationContext,"You have no access in this book", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<APiBookAccessResponses>, t: Throwable) {
                        Toast.makeText(applicationContext,"QR code is not Valid", Toast.LENGTH_SHORT).show()
                    }

                })
        }catch (e: Exception){
            Log.d("PlayVideo=", e.toString())
            Toast.makeText(applicationContext,"Try Again", Toast.LENGTH_SHORT).show()
        }

    }

}