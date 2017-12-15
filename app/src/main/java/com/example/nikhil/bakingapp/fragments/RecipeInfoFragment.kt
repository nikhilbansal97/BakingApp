package com.example.nikhil.bakingapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nikhil.bakingapp.Ingredient
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Step
import com.example.nikhil.bakingapp.activities.StepActivity
import com.example.nikhil.bakingapp.adapters.IngredientsAdapter
import com.example.nikhil.bakingapp.adapters.StepsAdapter

/**
 * Created by NIKHIL on 15-12-2017.
 */

class RecipeInfoFragment : Fragment(), StepsAdapter.OnStepsClicked{

    var recipeBundle: Bundle? = null
    var isTwoPane:Boolean = false
    var listener: OnTwoPaneStepsClicked? = null

    interface OnTwoPaneStepsClicked {
        fun twoPaneStepClicked(step: Step)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_recipe_info, container, false)

        val textView_name = view.findViewById<TextView>(R.id.recipeName)
        val textView_servings = view.findViewById<TextView>(R.id.recipeServings)
        val recycler_ingredients = view.findViewById<RecyclerView>(R.id.ingredientsList)
        val recycler_steps = view.findViewById<RecyclerView>(R.id.recipeSteps_recycler)

        if (recipeBundle != null) {
            textView_name.text = "Recipe : ${recipeBundle!!.getString("recipeName")}"
            textView_servings.text = "Servings : ${recipeBundle!!.getInt("recipeServings")}"
        }

        val ingredientsList:ArrayList<Ingredient> = recipeBundle!!.getParcelableArrayList("recipeIngredients")

        val adapter = IngredientsAdapter(context, ingredientsList)
        recycler_ingredients.adapter = adapter
        recycler_ingredients.layoutManager = LinearLayoutManager(context)
        recycler_ingredients.itemAnimator = DefaultItemAnimator()

        val stepsList: ArrayList<Step> = recipeBundle!!.getParcelableArrayList("recipeSteps")

        val stepsAdapter = StepsAdapter(context, stepsList, this)
        recycler_steps.layoutManager = LinearLayoutManager(context)
        recycler_steps.itemAnimator = DefaultItemAnimator()
        recycler_steps.adapter = stepsAdapter

        return view
    }

    override fun onStepClicked(step: Step) {
        if (isTwoPane) {
            listener!!.twoPaneStepClicked(step)
        } else {
            val intent = Intent(context, StepActivity::class.java)
            intent.putExtra("step", step)
            startActivity(intent)
        }
    }

}
