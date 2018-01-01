package com.example.nikhil.bakingapp.activities;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.SimpleIdlingResource;
import com.example.nikhil.bakingapp.adapters.RecipeAdapter;
import com.example.nikhil.bakingapp.networking.NetworkUtils;
import com.example.nikhil.bakingapp.pojos.Recipe;
import com.example.nikhil.bakingapp.widget.RecipeIngredientsWidgetProvider;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClicked {

    private RecipeAdapter adapter;
    private ArrayList<Recipe> recipeList = new ArrayList<>();
    private Boolean widgetSelectionActivity = false;
    private String ACTION_WIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE";
    private Intent widgetIntent;
    private SimpleIdlingResource idlingResource;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        widgetIntent = getIntent();
        if (widgetIntent.getAction() == ACTION_WIDGET_CONFIGURE) {
            widgetSelectionActivity = true;
            Bundle bundle = widgetIntent.getExtras();
            int widgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("widgetId", widgetId);
            editor.commit();
        }

        getIdlingResource();

        RecyclerView recipeListView = findViewById(R.id.recipeListView);
        adapter = new RecipeAdapter(this, recipeList, this);
        recipeListView.setAdapter(adapter);
        recipeListView.setLayoutManager(new LinearLayoutManager(this));
        recipeListView.setItemAnimator(new DefaultItemAnimator());
    }

    public SimpleIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getIdlingResource();
        new RecipesAsyncTask().execute();
    }

    @Override
    public void onRecipeSelected(int position, ArrayList<Recipe> list) {
        Recipe recipe = recipeList.get(position);

        if (!widgetSelectionActivity) {
            Intent intent = new Intent(getBaseContext(), RecipeActivity.class);
            // Create Bundle to send in the Intent
            Bundle bundle = new Bundle();
            bundle.putInt("recipeId", recipe.getId());
            bundle.putString("recipeName", recipe.getName());
            bundle.putParcelableArrayList("recipeIngredients", recipe.getIngredients());
            bundle.putParcelableArrayList("recipeSteps", recipe.getSteps());
            bundle.putInt("recipeServings", recipe.getServings());
            bundle.putString("recipeImage", recipe.getImage());

            intent.putExtras(bundle);
            startActivity(intent);
        } else {

            NetworkUtils.ingredientsSelected.clear();
            NetworkUtils.ingredientsSelected.addAll(recipe.getIngredients());

            // Get the id of the widget
            Bundle bundle = widgetIntent.getExtras();
            int widgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(widgetId);

            appWidgetOptions.putString("recipeName", recipe.getName());
            appWidgetManager.updateAppWidgetOptions(widgetId, appWidgetOptions);

            Intent providerIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, RecipeIngredientsWidgetProvider.class);
            int[] ids = new int[1];
            ids[0] = widgetId;
            providerIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            sendBroadcast(providerIntent);

            // Close the activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    private class RecipesAsyncTask extends AsyncTask<Void, Void, ArrayList<Recipe>> {

        @Override
        protected void onPreExecute() {
            idlingResource.setIdleState(false);
        }

        @Override
        protected ArrayList<Recipe> doInBackground(Void... voids) {
            ArrayList<Recipe> recipes = new ArrayList<>();
            try {
                String responce = NetworkUtils.getRecipesResponse();
                recipes = NetworkUtils.getRecipesList(responce);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipes;
        }

        @Override
        protected void onPostExecute(ArrayList<Recipe> recipes) {
            idlingResource.setIdleState(true);
            recipeList = recipes;
            adapter.notifyDataSetChanged(recipes);
        }
    }
}
