package com.example.nikhil.bakingapp.activities

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.RemoteViews
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Recipe
import com.example.nikhil.bakingapp.SimpleIdlingResource
import com.example.nikhil.bakingapp.adapters.RecipeAdapter
import com.example.nikhil.bakingapp.networking.NetworkUtils
import com.example.nikhil.bakingapp.networking.NetworkUtils.Companion.getRecipesList
import com.example.nikhil.bakingapp.networking.NetworkUtils.Companion.getRecipesResponse
import com.example.nikhil.bakingapp.widget.IngredientsRemoteViewsService

class MainActivity : AppCompatActivity() , RecipeAdapter.OnRecipeClicked {

    companion object {
        var adapter: RecipeAdapter? = null
        var recipeList: ArrayList<Recipe> = ArrayList<Recipe>()
        var widgetSelectionActivity: Boolean = false
        var ACTION_WIDGET_CONFIGURE = "android.appwidget.action.APPWIDGET_CONFIGURE"
        lateinit var widgetIntent: Intent
        var idlingResource: SimpleIdlingResource? = null
    }

    fun getIdlingResource(): SimpleIdlingResource{
        if (idlingResource == null)
            idlingResource = SimpleIdlingResource()
        return idlingResource!!
    }

    override fun onStart() {
        super.onStart()
        RecipesAsyncTask().execute()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        widgetIntent = intent
        if (widgetIntent.action == ACTION_WIDGET_CONFIGURE) {
            widgetSelectionActivity = true
            var bundle = widgetIntent.extras
            var widgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
            var sharedPref = getSharedPreferences("BakingApp", Context.MODE_PRIVATE)
            var editor = sharedPref.edit()
            editor.putInt("widgetId", widgetId)
            editor.commit()
        }
        getIdlingResource()

        var recipeListView = findViewById<RecyclerView>(R.id.recipeListView)
        adapter = RecipeAdapter(baseContext, recipeList, this)
        recipeListView.adapter = adapter
        recipeListView.layoutManager = LinearLayoutManager(this)
        recipeListView.itemAnimator = DefaultItemAnimator()

    }

    class RecipesAsyncTask : AsyncTask<Unit, Unit, ArrayList<Recipe>>() {

        override fun onPreExecute() {
            idlingResource!!.setIdleState(false)
        }

        override fun doInBackground(vararg params: Unit?): ArrayList<Recipe>? {
            val response = getRecipesResponse()
            val recipes: ArrayList<Recipe> = getRecipesList(response)
            Log.d("MainActivity.kt",recipes[0].name)
            return recipes
        }

        override fun onPostExecute(result: ArrayList<Recipe>?) {
            idlingResource!!.setIdleState(true)
            recipeList = result!!
            adapter!!.notifyDatatSetChanged(result!!)
        }

    }

    // Callback for the Recipe clicked.
    override fun onRecipeSelected(position: Int, list: ArrayList<Recipe>) {

        var recipe = recipeList[position]

        if (!widgetSelectionActivity) {
            var intent = Intent(baseContext, RecipeActivity::class.java)
            // Create Bundle to send in the Intent
            var bundle = Bundle()
            bundle.putInt("recipeId", recipe.id)
            bundle.putString("recipeName", recipe.name)
            bundle.putParcelableArrayList("recipeIngredients", recipe.ingredients)
            bundle.putParcelableArrayList("recipeSteps", recipe.steps)
            bundle.putInt("recipeServings", recipe.servings)
            bundle.putString("recipeImage", recipe.image)

            intent.putExtras(bundle)
            startActivity(intent)
        } else {
            // Get AppWidgetManager and update the appWidgetOptions for the widget.
            var appWidgetManager = AppWidgetManager.getInstance(this)

            var ingredientList = recipe.ingredients

            NetworkUtils.ingredientsSelected.clear()
            NetworkUtils.ingredientsSelected.addAll(ingredientList)

            // Get the id of the widget
            var bundle = widgetIntent.extras
            var widgetId = bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)

            val views = RemoteViews(this.packageName, R.layout.recipe_ingredients_widget)
            views.setTextViewText(R.id.widgetRecipeName, recipe.name)

            var optionsBundle = Bundle()
            optionsBundle.putParcelableArrayList("ingredients", ingredientList)

            val intent = Intent(this, IngredientsRemoteViewsService::class.java)
            intent.data = Uri.fromParts("content", widgetId.toString(), null)
            intent.putExtras(optionsBundle)
            views.setRemoteAdapter(R.id.widgetIngredientListView, intent)

            // Instruct the widget manager to update the widget
         //   appWidgetManager.updateAppWidgetOptions(widgetId, optionsBundle)
            appWidgetManager.updateAppWidget(widgetId, views)

            // Close the activity
            val returnIntent = Intent()
            returnIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}
