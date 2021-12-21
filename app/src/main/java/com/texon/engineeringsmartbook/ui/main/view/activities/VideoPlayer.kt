package com.texon.engineeringsmartbook.ui.main.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class VideoPlayer : YouTubeBaseActivity() {

    private lateinit var token: String
    // private val bookAccess: ApiInterfaces.BookAccessInterface by lazy { RetrofitClient.getBookAccessByQRCode() }
    private val youtubeApiKey = "AIzaSyAsTcs67uHqf_SYtlXPsb0tnx2LorBZ2jw"
    private lateinit var youTubePlayerInt: YouTubePlayer.OnInitializedListener
    private var youtubeLink = "OS644jbpn6U"
    private lateinit var mHandler: Handler
    private lateinit var mPlayer: YouTubePlayer
    private var seekTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        youtubeLink = intent.getStringExtra("link").toString()
        setContentView(R.layout.activity_video_player)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        //binding.loader.layoutLoader.visibility = View.VISIBLE
        //btnPlay.visibility = View.VISIBLE
        playerView.visibility = View.VISIBLE
        //videoLength.progress = 1


        val sharedPreferences: SharedPreferences = getSharedPreferences("Session", MODE_PRIVATE)
        val session = sharedPreferences.getBoolean("session", false)
        if(!session){
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }else{
            token = sharedPreferences.getString("token", "")!!
        }

        //Toast.makeText(applicationContext, "$token ", Toast.LENGTH_SHORT).show()
        //getBookAccess()

        btnFullScreen.setOnClickListener {
            val orientation = this.resources.configuration.orientation
            if (mPlayer.isPlaying) {
                mPlayer.pause()
            }
            requestedOrientation = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            mPlayer.play()
        }

        videoLength.setOnSeekBarChangeListener(mVideoSeekBarChangeListener)
        mHandler = Handler()
        playVideo(youtubeLink)
        playerView.initialize(youtubeApiKey, youTubePlayerInt)


    }


    private fun playVideo(link: String){
        youTubePlayerInt = object : YouTubePlayer.OnInitializedListener{
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                mPlayer = p1!!
                p1.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL)
              //  p1.seekToMillis(12)
                p1.cueVideo(link)
                //val millis: Int = p1.currentTimeMillis

                // Add listeners to YouTubePlayer instance
                p1.setPlayerStateChangeListener(mPlayerStateChangeListener)
                p1.setPlaybackEventListener(playbackEventListener)
                //Log.d("PlayVideo Time", millis.toString())
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext,"Link is not working", Toast.LENGTH_SHORT).show()
                Log.d("PlayVideo=", "Link is not working")
            }

            private val playbackEventListener: YouTubePlayer.PlaybackEventListener =
                object : YouTubePlayer.PlaybackEventListener {
                    override fun onPlaying() {
                        mHandler.postDelayed(runnable, 100)
                        displayCurrentTime()
                    }

                    override fun onPaused() {
                        mHandler.removeCallbacks(runnable)
                    }

                    override fun onStopped() {
                        mHandler.removeCallbacks(runnable)
                    }

                    override fun onBuffering(b: Boolean) {}
                    override fun onSeekTo(i: Int) {
                        mHandler.postDelayed(runnable, 100)
                    }
                }

        }
    }


//    private fun getBookAccess(){
//        try {
//            bookAccess.getBook( qrCode, "Bearer $token")
//                .enqueue(object : Callback<APiBookAccessResponses> {
//                    override fun onResponse(
//                        call: Call<APiBookAccessResponses>,
//                        response: Response<APiBookAccessResponses>
//                    ) {
//                        Toast.makeText(applicationContext, response.body()?.data?.video_link,Toast.LENGTH_SHORT).show()
//                        response.body()?.data?.video_link?.let { playVideo(it) }
//                        binding.loader.layoutLoader.visibility = View.GONE
//                        binding.btnPlay.visibility = View.VISIBLE
//                        binding.player.visibility = View.VISIBLE
//                    }
//
//                    override fun onFailure(call: Call<APiBookAccessResponses>, t: Throwable) {
//                        Toast.makeText(applicationContext,"QR code is not Valid",Toast.LENGTH_SHORT).show()
//                    }
//
//                })
//        }catch (e: Exception){
//            Log.d("PlayVideo=", e.toString())
//            Toast.makeText(applicationContext,"Try Again",Toast.LENGTH_SHORT).show()
//        }
//
//    }

    private fun displayCurrentTime() {
        //To stop video 1.2s before it ends
        if (mPlayer.currentTimeMillis >= mPlayer.durationMillis - 1200L) {
            mPlayer.seekToMillis(0)
            mPlayer.pause()
        }
        val formattedTime = formatTime(mPlayer.durationMillis - mPlayer.currentTimeMillis)
        play_time.text = formattedTime
        val playPercent = (mPlayer.currentTimeMillis.toFloat() / mPlayer.durationMillis.toFloat() * 100).toInt()
        // update live progress
        videoLength.progress = playPercent
    }

    private fun formatTime(millis: Int): String {
        val seconds = millis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return (if (hours == 0) "" else "$hours:") + String.format("%02d:%02d", minutes % 60, seconds % 60)
    }

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            displayCurrentTime()
            mHandler.postDelayed(this, 100)
        }
    }

    var mPlayerStateChangeListener: YouTubePlayer.PlayerStateChangeListener = object :
        YouTubePlayer.PlayerStateChangeListener {
        override fun onLoading() {}
        override fun onLoaded(s: String) {}
        override fun onAdStarted() {}
        override fun onVideoStarted() {
            displayCurrentTime()
        }

        override fun onVideoEnded() {
            mPlayer.seekToMillis(0)
            mPlayer.pause()
        }

        override fun onError(errorReason: YouTubePlayer.ErrorReason) {}
    }


    public override fun onDestroy() {
        mPlayer.release()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        seekTime = mPlayer.currentTimeMillis
    }

    override fun onStop() {
        mPlayer.release()
        super.onStop()
    }

    // To switch fullScreen icon when orientation changes
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            btnFullScreen.setImageResource(R.drawable.ic_fullscreen_open_video)

        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            btnFullScreen.setImageResource(R.drawable.ic_fullscreen_close_video)
        }
    }

    private var mVideoSeekBarChangeListener: SeekBar.OnSeekBarChangeListener = object :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            val lengthPlayed = (mPlayer.durationMillis * seekBar.progress / 100).toLong()
            mPlayer.seekToMillis(lengthPlayed.toInt())
        }
    }


}