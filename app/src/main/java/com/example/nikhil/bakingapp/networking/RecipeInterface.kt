package com.example.nikhil.bakingapp.networking

import com.example.nikhil.bakingapp.Recipe

import java.util.ArrayList

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by NIKHIL on 14-12-2017.
 */

interface RecipeInterface {

    @GET("baking.json")
    fun recipeList(): Call<ArrayList<Recipe>>

}
