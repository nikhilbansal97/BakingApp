package com.example.nikhil.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.fragments.StepInfoFragment;
import com.example.nikhil.bakingapp.pojos.Step;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class StepActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Step step = intent.getParcelableExtra("step");

            StepInfoFragment stepInfoFragment = new StepInfoFragment();
            stepInfoFragment.setStep(step);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.stepFragmentContainer, stepInfoFragment)
                    .commit();
        }
    }
}
