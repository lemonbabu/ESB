package com.texon.engineeringsmartbook

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.texon.engineeringsmartbook.model.api.ApiInterfaces
import com.texon.engineeringsmartbook.model.api.RetrofitClient
import com.texon.engineeringsmartbook.model.data.APiBookAccessResponses
import com.texon.engineeringsmartbook.view.ui.auth.Login
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@DelicateCoroutinesApi
class VideoPlayer : YouTubeBaseActivity() {

    private lateinit var qrCode: String
    private lateinit var token: String
    private val bookAccess: ApiInterfaces.BookAccessInterface by lazy { RetrofitClient.getBookAccessByQRCode() }
    private val youtubeApiKey = "AIzaSyAsTcs67uHqf_SYtlXPsb0tnx2LorBZ2jw"
    private lateinit var youTubePlayerInt: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrCode = intent.getStringExtra("qrCode").toString()
        setContentView(R.layout.activity_video_player)

        val sharedPreferences: SharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences.getBoolean("session", false)
        if(!session){
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }else{
            token = sharedPreferences.getString("token", "")!!
        }

        //Toast.makeText(applicationContext, "$token ", Toast.LENGTH_SHORT).show()
        getBookAccess()

        btnPlay.setOnClickListener {
            btnPlay.visibility = View.INVISIBLE
            player.initialize(youtubeApiKey, youTubePlayerInt)
        }
    }

    private fun getBookAccess(){
        bookAccess.getBook( qrCode, "Bearer $token")
            .enqueue(object : Callback<APiBookAccessResponses> {
                override fun onResponse(
                    call: Call<APiBookAccessResponses>,
                    response: Response<APiBookAccessResponses>
                ) {
                    Toast.makeText(applicationContext, response.body()?.data?.video_link,Toast.LENGTH_SHORT).show()
                    response.body()?.data?.video_link?.let { playVideo(it) }
                }

                override fun onFailure(call: Call<APiBookAccessResponses>, t: Throwable) {
                    Toast.makeText(applicationContext,"QR code is not Valid",Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun playVideo(link: String){
        youTubePlayerInt = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(link)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,"Link is not working", Toast.LENGTH_SHORT).show()
            }
        }
    }

}