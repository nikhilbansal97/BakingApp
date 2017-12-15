package com.example.nikhil.bakingapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Recipe

import java.util.ArrayList

/**
 * Created by NIKHIL on 14-12-2017.
 */

class RecipeAdapter(context: Context, objects: ArrayList<Recipe>, listener: OnRecipeClicked): RecyclerView.Adapter<RecipeAdapter.ViewHolder>(){

    var mContext = context
    var recipeList = objects
    var recipeListener = listener

    interface OnRecipeClicked {
        fun onRecipeSelected(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.recipe_item, parent, false))
    }

    override fun getItemCount(): Int {
        if (recipeList == null)
            return 0
        Log.d("RecipeAdapter.kt","getItemCount() -> ${recipeList.size}")
        return recipeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var currentRecipe = recipeList[position]
        holder!!.nameTextView!!.text = currentRecipe.name
        holder!!.nameTextView!!.setOnClickListener(View.OnClickListener {
            recipeListener.onRecipeSelected(position)
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView:TextView? = null
        init {
            nameTextView = view.findViewById(R.id.recipe_name_CardView)
        }
    }

    fun notifyDatatSetChanged(list: ArrayList<Recipe>) {
        recipeList.clear()
        recipeList.addAll(list)
        Log.d("RecipeAdapter.kt", recipeList.size.toString())
        notifyDataSetChanged()
    }
}
