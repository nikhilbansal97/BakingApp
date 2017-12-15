package com.example.nikhil.bakingapp.activities

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Recipe
import com.example.nikhil.bakingapp.adapters.RecipeAdapter
import com.example.nikhil.bakingapp.networking.ApiClient
import com.example.nikhil.bakingapp.networking.NetworkUtils.Companion.getRecipesList
import com.example.nikhil.bakingapp.networking.NetworkUtils.Companion.getRecipesResponse
import com.example.nikhil.bakingapp.networking.RecipeInterface
import com.google.android.exoplayer2.SimpleExoPlayer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() , RecipeAdapter.OnRecipeClicked {

    companion object {
        var adapter: RecipeAdapter? = null
        var recipeList: ArrayList<Recipe> = ArrayList<Recipe>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recipeListView = findViewById<RecyclerView>(R.id.recipeListView)
        adapter = RecipeAdapter(baseContext, recipeList, this)
        recipeListView.adapter = adapter
        recipeListView.layoutManager = LinearLayoutManager(this)
        recipeListView.itemAnimator = DefaultItemAnimator()
        var recipeInterface = ApiClient.getClient().create(RecipeInterface::class.java)
        var call = recipeInterface.recipeList()

        RecipesAsyncTask().execute()
    }

    fun callback(fn:(Throwable?, Response<ArrayList<Recipe>>)-> Unit): Callback<ArrayList<Recipe>> {
        return object: Callback<ArrayList<Recipe>> {
            override fun onResponse(call: Call<ArrayList<Recipe>>?, response: Response<ArrayList<Recipe>>?) {
                var list = response!!.body()
                adapter!!.notifyDatatSetChanged(list!!)
            }

            override fun onFailure(call: Call<ArrayList<Recipe>>?, t: Throwable?) {

            }
        }
    }

    class RecipesAsyncTask : AsyncTask<Unit, Unit, ArrayList<Recipe>>() {

        override fun doInBackground(vararg params: Unit?): ArrayList<Recipe>? {
            val response = getRecipesResponse()
            val recipes: ArrayList<Recipe> = getRecipesList(response)
            Log.d("MainActivity.kt",recipes[0].name)
            return recipes
        }

        override fun onPostExecute(result: ArrayList<Recipe>?) {
            recipeList = result!!
            adapter!!.notifyDatatSetChanged(result!!)
        }

    }

    // Callback for the Recipe clicked.
    override fun onRecipeSelected(position: Int) {

        var intent = Intent(baseContext, RecipeActivity::class.java)
        var recipe = recipeList[position]

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
    }
}
