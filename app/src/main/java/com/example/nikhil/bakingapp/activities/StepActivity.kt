package com.example.nikhil.bakingapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.fragments.StepInfoFragment
import com.example.nikhil.bakingapp.pojos.Step

/**
 * Created by NIKHIL on 23-12-2017.
 */

class StepActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)

        if (savedInstanceState == null) {
            val intent = intent
            val step = intent.getParcelableExtra<Step>("step")

            val stepInfoFragment = StepInfoFragment()
            stepInfoFragment.setStep(step)

            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                    .add(R.id.stepFragmentContainer, stepInfoFragment)
                    .commit()
        }
    }
}
