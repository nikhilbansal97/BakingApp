package com.example.nikhil.bakingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nikhil.bakingapp.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by NIKHIL on 24-12-2017.
 */


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListIdlingTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource resource;

    @Before
    public void registerIdlingResource() {
        resource = rule.getActivity().getIdlingResource();
        // From Espresso 3.0.0 and above Espresso.registerIdlingResources() method is deprecated and IdlingRegistry class is used.
        IdlingRegistry.getInstance().register(resource);
    }

    @Test
    public void recipeIdlingResource() {
        onView(withId(R.id.recipeListView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.recipeName)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(resource);
    }
}
