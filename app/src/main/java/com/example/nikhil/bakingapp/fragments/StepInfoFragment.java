package com.example.nikhil.bakingapp.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.pojos.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class StepInfoFragment extends Fragment {

    private Step step;
    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;
    private TextView playerNotAvailableView;
    private Boolean playState = null;
    private long position = 0;

    public void setStep(Step step) {
        this.step = step;
    }

    public StepInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_info, container, false);

        if (savedInstanceState != null) {
            step = savedInstanceState.getParcelable("step");
            playState = savedInstanceState.getBoolean("playState");
            position = savedInstanceState.getLong("position");
        }
        TextView textView_shortDes = view.findViewById(R.id.stepShortDescription);
        TextView textView_Des = view.findViewById(R.id.stepDescription);
        ImageView stepThumbnail = view.findViewById(R.id.stepthumbnail);
        playerNotAvailableView = view.findViewById(R.id.playerNotAvailable);
        exoPlayerView = view.findViewById(R.id.simpleExoPlayerView);
        textView_shortDes.setText(step.getShortDescription());
        textView_Des.setText(step.getDescription());
        String thumbnailUrl = step.getThumbnail();
        if (thumbnailUrl == null || thumbnailUrl.isEmpty() || thumbnailUrl.contains(".mp4"))
            stepThumbnail.setVisibility(View.GONE);
        else
            Picasso.with(getContext()).load(thumbnailUrl).into(stepThumbnail);
        initializePlayer();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("step", step);
        outState.putBoolean("playState", exoPlayer.getPlayWhenReady());
        outState.putLong("position", exoPlayer.getCurrentPosition());
    }

    private void initializePlayer() {
        // Create Player
        TrackSelector trackSelector = new DefaultTrackSelector();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        // Attach Player to factory
        exoPlayerView.setPlayer(exoPlayer);
        // Get the Video URL
        String videoUrl = step.getVideoUrl();
        if ((videoUrl != null) && !videoUrl.isEmpty()) {
            exoPlayerView.setVisibility(View.VISIBLE);
            playerNotAvailableView.setVisibility(View.GONE);
            Uri VIDEO_URI = Uri.parse(videoUrl);
            MediaSource mediaSource = new ExtractorMediaSource(VIDEO_URI,
                    new DefaultHttpDataSourceFactory("ua"),
                    new DefaultExtractorsFactory(),
                    null, null);
            if(playState != null) {
                exoPlayer.setPlayWhenReady(playState);
                exoPlayer.seekTo(position);
            }
            exoPlayer.prepare(mediaSource);
        } else {
            exoPlayerView.setVisibility(View.GONE);
            playerNotAvailableView.setVisibility(View.VISIBLE);
        }
    }


    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}
