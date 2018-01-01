package com.example.nikhil.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.networking.NetworkUtils;
import com.example.nikhil.bakingapp.pojos.Ingredient;

import java.util.ArrayList;


public class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Ingredient> ingredientsList = new ArrayList<>();
    private Context context;
    private Intent intent;

    public IngredientsRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {
        ingredientsList = NetworkUtils.ingredientsSelected;
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        if (ingredientsList == null)
            return 0;
        return ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_ingredient);
        Ingredient ingredient = ingredientsList.get(position);
        views.setTextViewText(R.id.ingredientName, ingredient.getIngredient());
        views.setTextViewText(R.id.ingredientMeasure, String.valueOf(ingredient.getQuantity())+" "+String.valueOf(ingredient.getMeasure()));
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
