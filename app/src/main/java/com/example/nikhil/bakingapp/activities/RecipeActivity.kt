package com.example.nikhil.bakingapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.LinearLayout

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.fragments.RecipeInfoFragment
import com.example.nikhil.bakingapp.fragments.StepInfoFragment
import com.example.nikhil.bakingapp.pojos.Step

import java.util.ArrayList

/**
 * Created by NIKHIL on 23-12-2017.
 */

class RecipeActivity : AppCompatActivity(), RecipeInfoFragment.OnTwoPaneStepsClicked {
    private var isTwoPane: Boolean? = false
    private var fragmentManager: FragmentManager? = null
    private var bundle: Bundle? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        if (savedInstanceState == null) {
            val intent = intent
            bundle = intent.extras
            Log.d(TAG, "onCreate: bundle found in intent")
        } else {
            bundle = savedInstanceState.getBundle("receivedBundle")
            Log.d(TAG, "onCreate: Bundle found in saved state")
        }

        fragmentManager = supportFragmentManager

        val masterFragment = findViewById<LinearLayout>(R.id.masterFragmentLayoutContainer)
        if (masterFragment != null) {
            isTwoPane = true
        }
        if (isTwoPane!!) {
            val recipeInfoFragment = RecipeInfoFragment()
            recipeInfoFragment.setRecipeBundle(bundle!!)
            recipeInfoFragment.setTwoPane(true)
            recipeInfoFragment.setListener(this)

            val stepInfoFragment = StepInfoFragment()
            val steps = bundle!!.getParcelableArrayList<Step>("recipeSteps")
            stepInfoFragment.setStep(steps!![0])

            fragmentManager!!.beginTransaction()
                    .add(R.id.recipeInfoContainer, recipeInfoFragment)
                    .commit()

            fragmentManager!!.beginTransaction()
                    .add(R.id.stepFragmentContainer, stepInfoFragment)
                    .commit()
        } else {
            if (savedInstanceState == null) {
                val recipeInfoFragment = RecipeInfoFragment()
                recipeInfoFragment.setRecipeBundle(bundle!!)
                recipeInfoFragment.setTwoPane(false)
                Log.d(TAG, "notTwoPane")
                fragmentManager!!.beginTransaction()
                        .add(R.id.recipeInfoContainer, recipeInfoFragment)
                        .commit()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putBundle("receivedBundle", bundle)
        super.onSaveInstanceState(outState)
    }

    override fun twoPaneStepClicked(step: Step) {
        val stepInfoFragment = StepInfoFragment()
        stepInfoFragment.setStep(step)

        fragmentManager!!.beginTransaction()
                .replace(R.id.stepFragmentContainer, stepInfoFragment)
                .commit()
    }

    companion object {

        private val TAG = "RecipeActivity"
    }
}
