package com.texon.engineeringsmartbook.ui.main.view.activities

import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.ActivityExoPlayerBinding
import kotlinx.android.synthetic.main.activity_exo_player.*

class ExoPlayer : AppCompatActivity() {
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private lateinit var binding : ActivityExoPlayerBinding
    var fullscreen = false
    var sUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDownloadLink()

        binding.exoFullscreenIcon.setOnClickListener {
            if (fullscreen) {
                binding.exoFullscreenIcon.setImageResource(R.drawable.ic_fullscreen_open_video)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                if (supportActionBar != null) {
                    supportActionBar!!.show()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                val params = playerView.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                playerView.layoutParams = params
                fullscreen = false
            } else {
                binding.exoFullscreenIcon.setImageResource(R.drawable.ic_fullscreen_close_video)
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                val params = playerView.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                playerView.layoutParams = params
                fullscreen = true
            }
        }

    }

    private fun initializePlayer() {

        try {
            mediaDataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"))

            val mediaSource = ProgressiveMediaSource.Factory(mediaDataSourceFactory).createMediaSource(
                MediaItem.fromUri(sUrl))

            val mediaSourceFactory: MediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

            simpleExoPlayer = SimpleExoPlayer.Builder(this)
                .setMediaSourceFactory(mediaSourceFactory)
                .build()

            simpleExoPlayer.addMediaSource(mediaSource)

            simpleExoPlayer.playWhenReady = true

            binding.playerView.setShutterBackgroundColor(Color.TRANSPARENT)
            binding.playerView.player = simpleExoPlayer
            binding.playerView.requestFocus()
        }catch (e: Exception){
            Toast.makeText(applicationContext, "Player is not initialized!!", Toast.LENGTH_SHORT).show()
        }



    }

    private fun releasePlayer() {
        simpleExoPlayer.playWhenReady = false
    }

    public override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

    override fun onDestroy() {
        simpleExoPlayer.release()
        super.onDestroy()
    }

    companion object {
        const val STREAM_URL = "https://html5videoformatconverter.com/data/images/happyfit2.mp4"
    }


    private fun getDownloadLink(){
        try {
            val youtubeLink = "https://www.youtube.com/watch?v=oN428nn6dG0"

            object : YouTubeExtractor(this) {
                override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                    if (ytFiles != null) {
                        val itag = 22
                        sUrl = ytFiles[itag].url
                    }
                }
            }.extract(youtubeLink)

            Log.d("Download Link: ", sUrl)
            Log.d("Download Link: ", sUrl)
            Log.d("Download Link: ", sUrl)
        } catch (e: Exception){
            Log.d("Download Link: ", "Error to download!!")
        }
    }


}