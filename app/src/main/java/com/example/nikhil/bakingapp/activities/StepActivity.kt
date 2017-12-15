package com.example.nikhil.bakingapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Step
import com.example.nikhil.bakingapp.fragments.StepInfoFragment

class StepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)

        val intent = intent
        val step = intent.getParcelableExtra<Step>("step")

        val stepInfoFragment = StepInfoFragment()
        stepInfoFragment.step = step

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .add(R.id.stepFragmentContainer, stepInfoFragment)
                .commit()

    }
}
