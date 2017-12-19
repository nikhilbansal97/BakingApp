package com.example.nikhil.bakingapp.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.nikhil.bakingapp.Ingredient
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.R.id.recipeName
import com.example.nikhil.bakingapp.networking.NetworkUtils

/**
 * Created by NIKHIL on 16-12-2017.
 */
class IngredientsRemoteViewsService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return IngredientsRemoteViewsFactory(applicationContext, intent!!)
    }
}

class IngredientsRemoteViewsFactory(var context: Context, var intent: Intent): RemoteViewsService.RemoteViewsFactory {

    var mIngredientsList: ArrayList<Ingredient> = ArrayList<Ingredient>()

    override fun getViewAt(position: Int): RemoteViews {
        var views = RemoteViews(context.packageName, R.layout.list_item_ingredient)
        var ingredient = mIngredientsList[position]
        views.setTextViewText(R.id.ingredientName, ingredient.ingredient)
        views.setTextViewText(R.id.ingredientMeasure, "${ingredient.quantity} ${ingredient.measure}")
        return views
    }

    override fun onCreate() {
        mIngredientsList = NetworkUtils.ingredientsSelected
    }

    override fun onDataSetChanged() {
    }

    override fun getCount(): Int {
        if (mIngredientsList == null)
            return 0
        return NetworkUtils.ingredientsSelected.size
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
    }

}