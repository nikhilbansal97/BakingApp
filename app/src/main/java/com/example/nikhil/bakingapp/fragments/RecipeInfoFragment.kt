package com.example.nikhil.bakingapp.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.activities.StepActivity
import com.example.nikhil.bakingapp.adapters.IngredientsAdapter
import com.example.nikhil.bakingapp.adapters.StepsAdapter
import com.example.nikhil.bakingapp.pojos.Ingredient
import com.example.nikhil.bakingapp.pojos.Step

import java.util.ArrayList

/**
 * Created by NIKHIL on 23-12-2017.
 */

class RecipeInfoFragment : Fragment(), StepsAdapter.OnStepsClicked {
    private var recipeBundle: Bundle? = null
    private var isTwoPane: Boolean = false
    private var listener: OnTwoPaneStepsClicked? = null

    override fun onStepClicked(step: Step) {
        if (isTwoPane) {
            listener!!.twoPaneStepClicked(step)
        } else {
            val intent = Intent(context, StepActivity::class.java)
            intent.putExtra("step", step)
            startActivity(intent)
        }
    }

    interface OnTwoPaneStepsClicked {
        fun twoPaneStepClicked(step: Step)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_recipe_info, container, false)

        val textViewName = view.findViewById<TextView>(R.id.recipeName)
        val textViewServings = view.findViewById<TextView>(R.id.recipeServings)
        val recyclerIngredients = view.findViewById<RecyclerView>(R.id.ingredientsList)
        val recyclerSteps = view.findViewById<RecyclerView>(R.id.recipeSteps_recycler)

        if (savedInstanceState != null) {
            recipeBundle = savedInstanceState.getBundle("recipeBundle")
            Log.d(TAG, "onCreateView: savedInstanceState != null")
        } else {
            Log.d(TAG, "onCreateView: savedInstanceState == null")
        }

        if (recipeBundle != null) {
            textViewName.text = "Recipe : " + recipeBundle!!.getString("recipeName")!!
            textViewServings.text = "Servings : " + recipeBundle!!.getInt("recipeServings")
            val ingredientsList = recipeBundle!!.getParcelableArrayList<Ingredient>("recipeIngredients")

            val adapter = IngredientsAdapter(context, ingredientsList!!)
            recyclerIngredients.adapter = adapter
            recyclerIngredients.layoutManager = LinearLayoutManager(context)
            recyclerIngredients.itemAnimator = DefaultItemAnimator()

            val stepsList = recipeBundle!!.getParcelableArrayList<Step>("recipeSteps")

            val stepsAdapter = StepsAdapter(context, stepsList, this)
            recyclerSteps.layoutManager = LinearLayoutManager(context)
            recyclerSteps.itemAnimator = DefaultItemAnimator()
            recyclerSteps.adapter = stepsAdapter

        } else {
            Log.d("RecipeInfoFragment", "recipeBundle == null");
        }
        return view
    }

    fun setRecipeBundle(recipeBundle: Bundle) {
        this.recipeBundle = recipeBundle
    }

    fun setTwoPane(twoPane: Boolean) {
        isTwoPane = twoPane
    }

    fun setListener(listener: OnTwoPaneStepsClicked) {
        this.listener = listener
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putBundle("recipeBundle", recipeBundle)
    }

    companion object {
        private val TAG = "RecipeInfoFragment"
    }
}
