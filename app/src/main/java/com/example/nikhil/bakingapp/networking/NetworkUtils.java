package com.example.nikhil.bakingapp.networking;

import android.util.Log;

import com.example.nikhil.bakingapp.pojos.Ingredient;
import com.example.nikhil.bakingapp.pojos.Recipe;
import com.example.nikhil.bakingapp.pojos.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class NetworkUtils {

    public static ArrayList<Ingredient> ingredientsSelected = new ArrayList<>();
    public static String recipeSelected = new String();

    public static String getRecipesResponse() throws IOException {
        URL url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpConnection.getInputStream();
        Scanner scanner = new Scanner(inputStream);
        scanner.useDelimiter("\\A");

        Boolean hasInput = scanner.hasNext();
        if (hasInput) {
            return scanner.next();
        } else {
            return "";
        }
    }

    public static ArrayList<Recipe> getRecipesList(String response) {
        ArrayList<Recipe> recipeList = new ArrayList<>();
        try {
            JSONArray recipeArray = new JSONArray(response);
            for (int i = 0; i < recipeArray.length(); i++) {
                JSONObject recipeObject = recipeArray.getJSONObject(i);
                int id = recipeObject.getInt("id");
                String name = recipeObject.getString("name");
                ArrayList<Ingredient> ingredientsList = new ArrayList<>();
                JSONArray ingredients = recipeObject.getJSONArray("ingredients");
                for (int j = 0; j < ingredients.length(); j++) {
                    JSONObject ingredient = ingredients.getJSONObject(j);
                    int quantity = ingredient.getInt("quantity");
                    String measure = ingredient.getString("measure");
                    String ingredientName = ingredient.getString("ingredient");
                    ingredientsList.add(new Ingredient(quantity, measure, ingredientName));
                }
                ArrayList<Step> stepsList = new ArrayList<>();
                JSONArray steps = recipeObject.getJSONArray("steps");
                for (int j = 0; j < steps.length(); j++) {
                    JSONObject step = steps.getJSONObject(j);
                    int id_step = step.getInt("id");
                    String shortDescription = step.getString("shortDescription");
                    String description = step.getString("description");
                    String videoUrl = step.getString("videoURL");
                    String thumbnailUrl = step.getString("thumbnailURL");
                    stepsList.add(new Step(id_step, shortDescription, description, thumbnailUrl, videoUrl));
                }
                int servings = recipeObject.getInt("servings");
                String image = recipeObject.getString("image");
                Log.d("NetworkUtils.kt", name);
                recipeList.add(new Recipe(id, name, ingredientsList, stepsList, servings, image));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }
}
