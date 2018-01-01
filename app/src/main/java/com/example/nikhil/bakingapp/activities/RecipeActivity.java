package com.example.nikhil.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.nikhil.bakingapp.R;
import com.example.nikhil.bakingapp.fragments.RecipeInfoFragment;
import com.example.nikhil.bakingapp.fragments.StepInfoFragment;
import com.example.nikhil.bakingapp.pojos.Step;

import java.util.ArrayList;

/**
 * Created by NIKHIL on 23-12-2017.
 */

public class RecipeActivity extends AppCompatActivity implements RecipeInfoFragment.OnTwoPaneStepsClicked {

    private static final String TAG = "RecipeActivity";
    private Boolean isTwoPane = false;
    private FragmentManager fragmentManager;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            bundle = intent.getExtras();
            Log.d(TAG, "onCreate: bundle found in intent");
        } else { 
            bundle = savedInstanceState.getBundle("receivedBundle");
            Log.d(TAG, "onCreate: Bundle found in saved state");
        }

        fragmentManager = getSupportFragmentManager();

        LinearLayout masterFragment = findViewById(R.id.masterFragmentLayoutContainer);
        if (masterFragment != null) {
            isTwoPane = true;
        }
        if (isTwoPane) {
            RecipeInfoFragment recipeInfoFragment = new RecipeInfoFragment();
            recipeInfoFragment.setRecipeBundle(bundle);
            recipeInfoFragment.setTwoPane(true);
            recipeInfoFragment.setListener(this);

            StepInfoFragment stepInfoFragment = new StepInfoFragment();
            ArrayList<Step> steps = bundle.getParcelableArrayList("recipeSteps");
            stepInfoFragment.setStep(steps.get(0));

            fragmentManager.beginTransaction()
                    .add(R.id.recipeInfoContainer, recipeInfoFragment)
                    .commit();

            fragmentManager.beginTransaction()
                    .add(R.id.stepFragmentContainer, stepInfoFragment)
                    .commit();
        } else {
            if (savedInstanceState == null) {
                RecipeInfoFragment recipeInfoFragment = new RecipeInfoFragment();
                recipeInfoFragment.setRecipeBundle(bundle);
                recipeInfoFragment.setTwoPane(false);
                Log.d(TAG, "notTwoPane");
                fragmentManager.beginTransaction()
                        .add(R.id.recipeInfoContainer, recipeInfoFragment)
                        .commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle("receivedBundle", bundle);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void twoPaneStepClicked(Step step) {
        StepInfoFragment stepInfoFragment = new StepInfoFragment();
        stepInfoFragment.setStep(step);

        fragmentManager.beginTransaction()
                .replace(R.id.stepFragmentContainer, stepInfoFragment)
                .commit();
    }
}
