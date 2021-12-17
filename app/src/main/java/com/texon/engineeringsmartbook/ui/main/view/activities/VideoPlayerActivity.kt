package com.texon.engineeringsmartbook.ui.main.view.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.ui.main.viewModel.VideoPlayerActivityViewModel
import kotlinx.android.synthetic.main.activity_video_player2.*
import kotlinx.coroutines.DelicateCoroutinesApi
import java.lang.Exception
import android.annotation.SuppressLint as SuppressLint1


class VideoPlayerActivity : AppCompatActivity() {

    private var link = "https://www.youtube.com/watch?v="

    private var player: SimpleExoPlayer? = null
    private lateinit var model: VideoPlayerActivityViewModel

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var fullscreen = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        link += intent.getStringExtra("link").toString()
        setContentView(R.layout.activity_video_player2)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        model = ViewModelProvider(this)[VideoPlayerActivityViewModel::class.java]

        model.getData().observe(this, {
            fullscreen = it
            requestedOrientation = if(it == "fullscreen"){
                btnScreenRotation.setImageResource(R.drawable.ic_fullscreen_close_video)
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

            }else{
                btnScreenRotation.setImageResource(R.drawable.ic_fullscreen_open_video)
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

            }
        })

        //Toast.makeText(applicationContext, "$token ", Toast.LENGTH_SHORT).show()
        //getBookAccess()

        btnScreenRotation.setOnClickListener {
            if(fullscreen == "fullscreen"){
                model.setData("")

            }else{
                model.setData("fullscreen")

            }
        }


    }

    public override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUi()
        initializePlayer()
    }

    public override fun onPause() {
        super.onPause()
        releasePlayer()
    }


    public override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.run {
            playbackPosition = this.currentPosition
            currentWindow = this.currentWindowIndex
            playWhenReady = this.playWhenReady
            release()
        }
        player = null
    }

    private fun hideSystemUi() {
        video_view.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private fun initializePlayer() {
        try{
            player = SimpleExoPlayer.Builder(this).build()
            video_view.player = player


            object : YouTubeExtractor(this) {
                override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                    if (ytFiles != null) {
                        val itag = 110
                        val audioTag = 140

                        val audioSource: MediaSource = ProgressiveMediaSource
                            .Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles[audioTag].url))

                        val videoSource: MediaSource = ProgressiveMediaSource
                            .Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(ytFiles[itag].url))

                        player!!.setMediaSource(MergingMediaSource(
                            true,videoSource,audioSource
                        ), true)
                        player!!.prepare()
                        player!!.playWhenReady = playWhenReady
                        player!!.seekTo(currentWindow,playbackPosition)
                    }
                }
            }.extract(link, false, false)


        } catch (e: Exception){
            Toast.makeText(applicationContext, "Loading Problem Please Try again...", Toast.LENGTH_SHORT).show()
        }
    }




}

