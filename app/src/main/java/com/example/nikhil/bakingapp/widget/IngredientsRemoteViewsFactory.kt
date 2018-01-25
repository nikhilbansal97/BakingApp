package com.example.nikhil.bakingapp.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.networking.NetworkUtils
import com.example.nikhil.bakingapp.pojos.Ingredient

import java.util.ArrayList


class IngredientsRemoteViewsFactory(private val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private var ingredientsList: ArrayList<Ingredient> = ArrayList()

    override fun onCreate() {
        ingredientsList = NetworkUtils.ingredientsSelected
    }

    override fun onDataSetChanged() {}

    override fun onDestroy() {}

    override fun getCount(): Int {
        return if (ingredientsList == null) 0 else ingredientsList.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.list_item_ingredient)
        val ingredient = ingredientsList[position]
        views.setTextViewText(R.id.ingredientName, ingredient.ingredient)
        views.setTextViewText(R.id.ingredientMeasure, ingredient.quantity.toString() + " " + ingredient.measure)
        return views
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }
}
