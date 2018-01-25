package com.example.nikhil.bakingapp.fragments

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.pojos.Step
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.squareup.picasso.Picasso

/**
 * Created by NIKHIL on 23-12-2017.
 */

class StepInfoFragment : Fragment() {

    private var step: Step? = null
    private var exoPlayerView: SimpleExoPlayerView? = null
    private var exoPlayer: SimpleExoPlayer? = null
    private var playerNotAvailableView: TextView? = null
    private var playState: Boolean? = null
    private var position: Long = 0

    fun setStep(step: Step) {
        this.step = step
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_step_info, container, false)

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable("step")
            playState = savedInstanceState.getBoolean("playState")
            position = savedInstanceState.getLong("position")
        }
        val textView_shortDes = view.findViewById<TextView>(R.id.stepShortDescription)
        val textView_Des = view.findViewById<TextView>(R.id.stepDescription)
        val stepThumbnail = view.findViewById<ImageView>(R.id.stepthumbnail)
        playerNotAvailableView = view.findViewById(R.id.playerNotAvailable)
        exoPlayerView = view.findViewById(R.id.simpleExoPlayerView)
        textView_shortDes.text = step!!.shortDescription
        textView_Des.text = step!!.description
        val thumbnailUrl = step!!.thumbnail
        if (thumbnailUrl == null || thumbnailUrl.isEmpty() || thumbnailUrl.contains(".mp4"))
            stepThumbnail.visibility = View.GONE
        else
            Picasso.with(context).load(thumbnailUrl).into(stepThumbnail)
        initializePlayer()
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putParcelable("step", step)
        outState.putBoolean("playState", exoPlayer!!.playWhenReady)
        outState.putLong("position", exoPlayer!!.currentPosition)
    }

    private fun initializePlayer() {
        // Create Player
        val trackSelector = DefaultTrackSelector()
        exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        // Attach Player to factory
        exoPlayerView!!.player = exoPlayer
        // Get the Video URL
        val videoUrl = step!!.videoUrl
        if (videoUrl != null && !videoUrl.isEmpty()) {
            exoPlayerView!!.visibility = View.VISIBLE
            playerNotAvailableView!!.visibility = View.GONE
            val VIDEO_URI = Uri.parse(videoUrl)
            val mediaSource = ExtractorMediaSource(VIDEO_URI,
                    DefaultHttpDataSourceFactory("ua"),
                    DefaultExtractorsFactory(), null, null)
            if (playState != null) {
                exoPlayer!!.playWhenReady = playState!!
                exoPlayer!!.seekTo(position)
            }
            exoPlayer!!.prepare(mediaSource)
        } else {
            exoPlayerView!!.visibility = View.GONE
            playerNotAvailableView!!.visibility = View.VISIBLE
        }
    }


    private fun releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer!!.stop()
            exoPlayer!!.release()
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
