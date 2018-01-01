package com.example.nikhil.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.activities.StepActivity;
import com.example.nikhil.bakingapp.adapters.IngredientsAdapter;
import com.example.nikhil.bakingapp.adapters.StepsAdapter;
import com.example.nikhil.bakingapp.pojos.Ingredient;
import com.example.nikhil.bakingapp.pojos.Step;

import java.util.ArrayList;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class RecipeInfoFragment extends Fragment implements StepsAdapter.OnStepsClicked {

    private static final String TAG = "RecipeInfoFragment";
    private Bundle recipeBundle;
    private Boolean isTwoPane = false;
    private OnTwoPaneStepsClicked listener;

    @Override
    public void onStepClicked(Step step) {
        if (isTwoPane) {
            listener.twoPaneStepClicked(step);
        } else {
            Intent intent = new Intent(getContext(), StepActivity.class);
            intent.putExtra("step", step);
            startActivity(intent);
        }
    }

    public interface OnTwoPaneStepsClicked {
        void twoPaneStepClicked(Step step);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);

        TextView textView_name = view.findViewById(R.id.recipeName);
        TextView textView_servings = view.findViewById(R.id.recipeServings);
        RecyclerView recycler_ingredients = view.findViewById(R.id.ingredientsList);
        RecyclerView recycler_steps = view.findViewById(R.id.recipeSteps_recycler);

        if (savedInstanceState != null) {
            recipeBundle = savedInstanceState.getBundle("recipeBundle");
            Toast.makeText(getContext(), "savedInstanceState != null", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreateView: savedInstanceState != null");
        } else {
            Toast.makeText(getContext(), "savedInstanceState == null", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onCreateView: savedInstanceState == null");
        }

        if (recipeBundle != null) {
            textView_name.setText("Recipe : " + recipeBundle.getString("recipeName"));
            textView_servings.setText("Servings : " + recipeBundle.getInt("recipeServings"));
            ArrayList<Ingredient> ingredientsList = recipeBundle.getParcelableArrayList("recipeIngredients");

            IngredientsAdapter adapter = new IngredientsAdapter(getContext(), ingredientsList);
            recycler_ingredients.setAdapter(adapter);
            recycler_ingredients.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_ingredients.setItemAnimator(new DefaultItemAnimator());

            ArrayList<Step> stepsList = recipeBundle.getParcelableArrayList("recipeSteps");

            StepsAdapter stepsAdapter = new StepsAdapter(getContext(), stepsList, this);
            recycler_steps.setLayoutManager(new LinearLayoutManager(getContext()));
            recycler_steps.setItemAnimator(new DefaultItemAnimator());
            recycler_steps.setAdapter(stepsAdapter);
            
        } else {
            Toast.makeText(getContext(), "recipeBundle == null", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    public void setRecipeBundle(Bundle recipeBundle) {
        this.recipeBundle = recipeBundle;
    }

    public void setTwoPane(Boolean twoPane) {
        isTwoPane = twoPane;
    }

    public void setListener(OnTwoPaneStepsClicked listener) {
        this.listener = listener;
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("recipeBundle", recipeBundle);
    }

    public RecipeInfoFragment() {
    }
}
