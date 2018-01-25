package com.example.nikhil.bakingapp.activities

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.SimpleIdlingResource
import com.example.nikhil.bakingapp.adapters.RecipeAdapter
import com.example.nikhil.bakingapp.networking.NetworkUtils
import com.example.nikhil.bakingapp.pojos.Recipe
import com.example.nikhil.bakingapp.widget.RecipeIngredientsWidgetProvider

import java.io.IOException
import java.util.ArrayList

/**
 * Created by NIKHIL on 23-12-2017.
 */

class MainActivity : AppCompatActivity(), RecipeAdapter.OnRecipeClicked {

    private lateinit var adapter: RecipeAdapter
    private var recipeList = ArrayList<Recipe>()
    private var widgetSelectionActivity: Boolean = false
    private val ACTION_WIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE"
    private var widgetIntent: Intent? = null
    private var idlingResource: SimpleIdlingResource? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        widgetIntent = intent
        if (widgetIntent!!.action === ACTION_WIDGET_CONFIGURE) {
            widgetSelectionActivity = true
            val bundle = widgetIntent!!.extras
            val widgetId = bundle!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            val prefs = getSharedPreferences(packageName, Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putInt("widgetId", widgetId)
            editor.commit()
        }

        getIdlingResource()

        val recipeListView = findViewById<RecyclerView>(R.id.recipeListView)
        adapter = RecipeAdapter(this, recipeList, this)
        recipeListView.adapter = adapter
        recipeListView.layoutManager = LinearLayoutManager(this)
        recipeListView.itemAnimator = DefaultItemAnimator()
    }

    fun getIdlingResource(): SimpleIdlingResource {
        if (idlingResource == null) {
            idlingResource = SimpleIdlingResource()
        }
        return idlingResource!!
    }

    override fun onStart() {
        super.onStart()
        getIdlingResource()
        RecipesAsyncTask().execute()
    }

    override fun onRecipeSelected(position: Int, list: ArrayList<Recipe>) {
        val recipe = recipeList[position]

        if (widgetSelectionActivity.not()) {
            val intent = Intent(baseContext, RecipeActivity::class.java)
            // Create Bundle to send in the Intent
            val bundle = Bundle()
            bundle.putInt("recipeId", recipe.id)
            bundle.putString("recipeName", recipe.name)
            bundle.putParcelableArrayList("recipeIngredients", recipe.ingredients)
            bundle.putParcelableArrayList("recipeSteps", recipe.steps)
            bundle.putInt("recipeServings", recipe.servings)
            bundle.putString("recipeImage", recipe.image)

            intent.putExtras(bundle)
            startActivity(intent)
        } else {

            NetworkUtils.ingredientsSelected.clear()
            NetworkUtils.ingredientsSelected.addAll(recipe.ingredients)

            // Get the id of the widget
            val bundle = widgetIntent!!.extras
            val widgetId = bundle!!.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

            val appWidgetManager = AppWidgetManager.getInstance(this)
            val appWidgetOptions = appWidgetManager.getAppWidgetOptions(widgetId)

            appWidgetOptions.putString("recipeName", recipe.name)
            appWidgetManager.updateAppWidgetOptions(widgetId, appWidgetOptions)

            val providerIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE, null, this, RecipeIngredientsWidgetProvider::class.java)
            val ids = IntArray(1)
            ids[0] = widgetId
            providerIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            sendBroadcast(providerIntent)

            // Close the activity
            val returnIntent = Intent()
            returnIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    private inner class RecipesAsyncTask : AsyncTask<Void, Void, ArrayList<Recipe>>() {

        override fun onPreExecute() {
            idlingResource!!.setIdleState(false)
        }

        override fun doInBackground(vararg voids: Void): ArrayList<Recipe> {
            var recipes = ArrayList<Recipe>()
            try {
                val response = NetworkUtils.recipesResponse
                recipes = NetworkUtils.getRecipesList(response)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return recipes
        }

        override fun onPostExecute(recipes: ArrayList<Recipe>) {
            idlingResource!!.setIdleState(true)
            recipeList = recipes
            adapter.notifyDataSetChanged(recipes)
        }
    }
}
