package com.example.nikhil.bakingapp.networking

import android.util.Log

import com.example.nikhil.bakingapp.pojos.Ingredient
import com.example.nikhil.bakingapp.pojos.Recipe
import com.example.nikhil.bakingapp.pojos.Step

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.ArrayList
import java.util.Scanner

/**
 * Created by NIKHIL on 23-12-2017.
 */

object NetworkUtils {

    var ingredientsSelected = ArrayList<Ingredient>()

    val recipesResponse: String
        @Throws(IOException::class)
        get() {
            val url = URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json")
            val httpConnection = url.openConnection() as HttpURLConnection
            val inputStream = httpConnection.inputStream
            val scanner = Scanner(inputStream)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                ""
            }
        }

    fun getRecipesList(response: String): ArrayList<Recipe> {
        val recipeList = ArrayList<Recipe>()
        try {
            val recipeArray = JSONArray(response)
            for (i in 0 until recipeArray.length()) {
                val recipeObject = recipeArray.getJSONObject(i)
                val id = recipeObject.getInt("id")
                val name = recipeObject.getString("name")
                val ingredientsList = ArrayList<Ingredient>()
                val ingredients = recipeObject.getJSONArray("ingredients")
                for (j in 0 until ingredients.length()) {
                    val ingredient = ingredients.getJSONObject(j)
                    val quantity = ingredient.getInt("quantity")
                    val measure = ingredient.getString("measure")
                    val ingredientName = ingredient.getString("ingredient")
                    ingredientsList.add(Ingredient(quantity, measure, ingredientName))
                }
                val stepsList = ArrayList<Step>()
                val steps = recipeObject.getJSONArray("steps")
                for (j in 0 until steps.length()) {
                    val step = steps.getJSONObject(j)
                    val idStep = step.getInt("id")
                    val shortDescription = step.getString("shortDescription")
                    val description = step.getString("description")
                    val videoUrl = step.getString("videoURL")
                    val thumbnailUrl = step.getString("thumbnailURL")
                    stepsList.add(Step(idStep, shortDescription, description, thumbnailUrl, videoUrl))
                }
                val servings = recipeObject.getInt("servings")
                val image = recipeObject.getString("image")
                Log.d("NetworkUtils.kt", name)
                recipeList.add(Recipe(id, name, ingredientsList, stepsList, servings, image))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return recipeList
    }
}
