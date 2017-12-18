package com.example.nikhil.bakingapp

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4

import com.example.nikhil.bakingapp.activities.MainActivity

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.example.nikhil.bakingapp.activities.RecipeActivity
import com.example.nikhil.bakingapp.adapters.RecipeAdapter
import com.example.nikhil.bakingapp.adapters.StepsAdapter

/**
 * Created by NIKHIL on 17-12-2017.
 */

@RunWith(AndroidJUnit4::class)
@LargeTest
class RecipeListIdlingTest {

    @get:Rule
    var rule = ActivityTestRule(MainActivity::class.java)

    private var resource: IdlingResource? = null

    @Before
    fun registerIdlingResource() {
        resource = rule.activity.getIdlingResource()
        // From Espresso 3.0.0 and above Espresso.registerIdlingResources() method is deprecated and IdlingRegistry class is used.
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun testing() {
        onView(withId(R.id.recipeListView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecipeAdapter.ViewHolder>(1, click()))
        onView(withId(R.id.recipeName)).check(matches(isDisplayed()))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(resource)
    }
}
