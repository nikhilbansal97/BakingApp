package com.example.nikhil.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.networking.NetworkUtils;

/**
 * Created by NIKHIL on 24-12-2017.
 */

public class RecipeIngredientsWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId: appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        NetworkUtils.ingredientsSelected.clear();
    }

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_widget);
        Intent intent = new Intent(context, IngredientsRemoteViewsService.class);
        views.setRemoteAdapter(R.id.widgetIngredientListView, intent);
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        String recipeName = "  ";
        if (appWidgetOptions.containsKey("recipeName")) {
            recipeName = appWidgetOptions.getString("recipeName");
        }
        views.setTextViewText(R.id.widgetRecipeName, recipeName);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}
