package com.example.nikhil.bakingapp.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Step
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.SimpleExoPlayerView


/**
 * Created by NIKHIL on 15-12-2017.
 */
class StepInfoFragment: Fragment() {

    lateinit var step: Step
    lateinit var exoplayerView: SimpleExoPlayerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_step_info, container, false)

        val textView_shortDes = view!!.findViewById<TextView>(R.id.stepShortDescription)
        val textView_Des = view!!.findViewById<TextView>(R.id.stepDescription)
        exoplayerView = view!!.findViewById<SimpleExoPlayerView>(R.id.simpleExoPlayerView)

        textView_shortDes.text = step.shortDescription
        textView_Des.text = step.description
        initializePlayer()

        return view
    }

    fun initializePlayer() {
        val trackSelector = DefaultTrackSelector()
    }

}