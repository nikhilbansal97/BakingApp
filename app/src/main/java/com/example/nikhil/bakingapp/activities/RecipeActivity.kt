package com.example.nikhil.bakingapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.widget.LinearLayout
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Step
import com.example.nikhil.bakingapp.adapters.StepsAdapter
import com.example.nikhil.bakingapp.fragments.RecipeInfoFragment
import com.example.nikhil.bakingapp.fragments.StepInfoFragment

class RecipeActivity : AppCompatActivity(), RecipeInfoFragment.OnTwoPaneStepsClicked {

    var isTwoPane: Boolean = false
    var fragmentManager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        // Get intent and bundled information
        val intent = intent
        val bundle = intent.extras

        fragmentManager = supportFragmentManager

        var masterFragment = findViewById<LinearLayout>(R.id.masterFragmentLayoutContainer)
        if (masterFragment != null)
            isTwoPane = true

        if (isTwoPane) {

            val recipeInfoFragment = RecipeInfoFragment()
            recipeInfoFragment.recipeBundle = bundle
            recipeInfoFragment.isTwoPane = true
            recipeInfoFragment.listener = this

            val stepInfoFragment = StepInfoFragment()
            var steps = bundle.getParcelableArrayList<Step>("recipeSteps")
            stepInfoFragment.step = steps[0]

            fragmentManager!!.beginTransaction()
                    .add(R.id.recipeInfoContainer, recipeInfoFragment)
                    .commit()

            fragmentManager!!.beginTransaction()
                    .add(R.id.stepFragmentContainer, stepInfoFragment)
                    .commit()
        } else {
            val recipeInfoFragment = RecipeInfoFragment()
            recipeInfoFragment.recipeBundle = bundle
            recipeInfoFragment.isTwoPane= false

            fragmentManager!!.beginTransaction()
                    .add(R.id.recipeInfoContainer, recipeInfoFragment)
                    .commit()
        }
    }

    override fun twoPaneStepClicked(step: Step) {
        var stepInfoFragment = StepInfoFragment()
        stepInfoFragment.step = step

        fragmentManager!!.beginTransaction()
                .replace(R.id.stepFragmentContainer, stepInfoFragment)
                .commit()
    }
}
