package com.example.nikhil.bakingapp.networking

import android.util.Log
import com.example.nikhil.bakingapp.Ingredient
import com.example.nikhil.bakingapp.Recipe
import com.example.nikhil.bakingapp.Step
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by NIKHIL on 14-12-2017.
 */

class NetworkUtils {

    companion object {

        fun getRecipesResponse(): String {
            // Create URL
            val url = URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
            // Make a HttpURLConnection
            val httpConnection = url.openConnection()
            // Get input stream
            val inputStream = httpConnection.getInputStream()
            // Create a scanner to scan the response
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")

            var hasInput = scanner.hasNext()
            if (hasInput) {
                return scanner.next()
            } else {
                return ""
            }
        }

        fun getRecipesList(response: String): ArrayList<Recipe> {
            val recipeList = ArrayList<Recipe>()
            val recipeArray = JSONArray(response)
            for (i in 0 until recipeArray.length()) {
                var recipeObject = recipeArray.getJSONObject(i)
                var id = recipeObject.getInt("id")
                var name = recipeObject.getString("name")
                var ingredientsList = ArrayList<Ingredient>()
                var ingredients = recipeObject.getJSONArray("ingredients")
                for (j in 0 until ingredients.length()) {
                    var ingredient = ingredients.getJSONObject(j)
                    var quantity = ingredient.getInt("quantity")
                    var measure = ingredient.getString("measure")
                    var ingredientName = ingredient.getString("ingredient")
                    ingredientsList.add(Ingredient(quantity, measure, ingredientName))
                }
                var stepsList = ArrayList<Step>()
                var steps = recipeObject.getJSONArray("steps")
                for (j in 0 until steps.length()) {
                    var step = steps.getJSONObject(j)
                    var id = step.getInt("id")
                    var shortDescription = step.getString("shortDescription")
                    var description = step.getString("description")
                    var videoUrl = step.getString("videoURL")
                    var thumbnail = "not_available"
                    stepsList.add(Step(id, shortDescription, description, thumbnail, videoUrl))
                }
                var servings = recipeObject.getInt("servings")
                var image = recipeObject.getString("image")
                Log.d("NetworkUtils.kt", name)
                recipeList.add(Recipe(id, name, ingredientsList, stepsList, servings, image))
            }
            return recipeList
        }
    }
}
