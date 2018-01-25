package com.example.nikhil.bakingapp.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.networking.NetworkUtils

/**
 * Created by NIKHIL on 24-12-2017.
 */

class RecipeIngredientsWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        NetworkUtils.ingredientsSelected.clear()
    }

    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.recipe_ingredients_widget)
        val intent = Intent(context, IngredientsRemoteViewsService::class.java)
        views.setRemoteAdapter(R.id.widgetIngredientListView, intent)
        val appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId)
        var recipeName: String? = "  "
        if (appWidgetOptions.containsKey("recipeName")) {
            recipeName = appWidgetOptions.getString("recipeName")
        }
        views.setTextViewText(R.id.widgetRecipeName, recipeName)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}
