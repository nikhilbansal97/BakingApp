package com.example.nikhil.bakingapp.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Step
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory


/**
 * Created by NIKHIL on 15-12-2017.
 */
class StepInfoFragment: Fragment() {

    lateinit var step: Step
    lateinit var exoplayerView: SimpleExoPlayerView
    lateinit var exoPlayer: SimpleExoPlayer
    lateinit var playerNotAvailableView: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_step_info, container, false)

        val textView_shortDes = view!!.findViewById<TextView>(R.id.stepShortDescription)
        val textView_Des = view!!.findViewById<TextView>(R.id.stepDescription)
        playerNotAvailableView = view!!.findViewById(R.id.playerNotAvailable)
        exoplayerView = view!!.findViewById<SimpleExoPlayerView>(R.id.simpleExoPlayerView)

        textView_shortDes.text = step.shortDescription
        textView_Des.text = step.description
        initializePlayer()

        return view
    }

    private fun initializePlayer() {

        // Create Player
        val trackSelector = DefaultTrackSelector()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        // Attach Player to factory
        exoplayerView!!.player = exoPlayer
        // Get the Video URL
        var videoUrl:String? = step.videoUrl
        if ((videoUrl != null) and videoUrl!!.isNotEmpty()){
            exoplayerView.visibility = View.VISIBLE
            playerNotAvailableView.visibility = View.GONE
            val VIDEO_URI = Uri.parse(videoUrl)
            val mediaSource = ExtractorMediaSource(VIDEO_URI,
                    DefaultHttpDataSourceFactory("ua"),
                    DefaultExtractorsFactory(),
                    null, null)
            exoPlayer.prepare(mediaSource)
        } else {
            exoplayerView.visibility = View.GONE
            playerNotAvailableView.visibility = View.VISIBLE
        }
    }

    private fun releasePlayer(){
        if (exoPlayer != null){
            exoPlayer.stop()
            exoPlayer.release()
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }
}