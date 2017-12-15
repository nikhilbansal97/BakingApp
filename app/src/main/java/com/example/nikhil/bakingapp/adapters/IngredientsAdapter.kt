package com.example.nikhil.bakingapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.nikhil.bakingapp.Ingredient
import com.example.nikhil.bakingapp.R

/**
 * Created by NIKHIL on 15-12-2017.
 */

class IngredientsAdapter(context: Context, list: ArrayList<Ingredient>) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    var mContext: Context = context
    var ingredientsList: ArrayList<Ingredient> = list

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): IngredientsViewHolder {
        return IngredientsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_ingredient, parent, false))
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder?, position: Int) {
        val ingredient = ingredientsList[position]

        holder!!.textView_name!!.text = ingredient.ingredient
        holder!!.textView_measure!!.text = "${ingredient.quantity} ${ingredient.measure}"

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView_name: TextView = itemView.findViewById(R.id.ingredientName)
        var textView_measure: TextView = itemView.findViewById(R.id.ingredientMeasure)
    }
}