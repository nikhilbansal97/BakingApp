package com.example.nikhil.bakingapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.pojos.Recipe
import com.squareup.picasso.Picasso

import java.util.ArrayList

/**
 * Created by NIKHIL on 23-12-2017.
 */

class RecipeAdapter(private val context: Context, recipeList: ArrayList<Recipe>, private val recipeListener: OnRecipeClicked) : RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    private var recipeList = ArrayList<Recipe>()

    init {
        this.recipeList = recipeList
    }

    fun notifyDataSetChanged(recipes: ArrayList<Recipe>) {
        recipeList.clear()
        recipeList.addAll(recipes)
        notifyDataSetChanged()
    }

    interface OnRecipeClicked {
        fun onRecipeSelected(position: Int, list: ArrayList<Recipe>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.nameTextView.text = currentRecipe.name
        holder.nameTextView.setOnClickListener { recipeListener.onRecipeSelected(position, recipeList) }
        if (currentRecipe.image.isEmpty()) {
            holder.nameImageView.visibility = View.GONE
        } else {
            Picasso.get().load(currentRecipe.image).into(holder.nameImageView)
        }
    }

    override fun getItemCount(): Int {
        return recipeList?.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView = itemView.findViewById(R.id.recipe_name_CardView)
        val nameImageView: ImageView = itemView.findViewById(R.id.recipeNameImageView)

    }

}
