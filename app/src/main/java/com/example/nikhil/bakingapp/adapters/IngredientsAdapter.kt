package com.example.nikhil.bakingapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.pojos.Ingredient

import java.util.ArrayList

/**
 * Created by NIKHIL on 23-12-2017.
 */

class IngredientsAdapter(private val context: Context, ingredientsList: ArrayList<Ingredient>) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = ArrayList<Ingredient>()

    init {
        this.ingredientsList = ingredientsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = ingredientsList[position]
        holder.textViewName.text = ingredient.ingredient
        holder.textViewMeasure.text = ingredient.quantity.toString() + " " + ingredient.measure.toString()
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName: TextView = itemView.findViewById(R.id.ingredientName)
        val textViewMeasure: TextView = itemView.findViewById(R.id.ingredientMeasure)
    }
}
