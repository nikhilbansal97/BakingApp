package com.example.nikhil.bakingapp.pojos

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by NIKHIL on 25-01-2018.
 */

@SuppressLint("ParcelCreator")
@Parcelize
data class Ingredient(
        var quantity: Int = 0,
        var measure: String,
        var ingredient: String
): Parcelable


@SuppressLint("ParcelCreator")
@Parcelize
data class Recipe(
        var id: Int,
        var name: String,
        var ingredients: ArrayList<Ingredient>,
        var steps: ArrayList<Step>,
        var servings: Int,
        var image: String
): Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class Step(
        var id: Int,
        var shortDescription: String,
        var description: String,
        var thumbnail: String,
        var videoUrl: String
):Parcelable