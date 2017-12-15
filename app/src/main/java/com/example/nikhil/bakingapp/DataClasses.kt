package com.example.nikhil.bakingapp

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by NIKHIL on 14-12-2017.
 */
data class Recipe(
        @SerializedName("id")
        var id: Int,
        @SerializedName("name")
        var name: String,
        @SerializedName("ingredients")
        var ingredients: ArrayList<Ingredient>,
        @SerializedName("steps")
        var steps: ArrayList<Step>,
        @SerializedName("servings")
        var servings: Int,
        @SerializedName("image")
        var image: String
)

data class Ingredient(
        @SerializedName("quantity")
        var quantity: Int,
        @SerializedName("measure")
        var measure: String,
        @SerializedName("ingredient")
        var ingredient: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(quantity)
        parcel.writeString(measure)
        parcel.writeString(ingredient)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ingredient> {
        override fun createFromParcel(parcel: Parcel): Ingredient {
            return Ingredient(parcel)
        }

        override fun newArray(size: Int): Array<Ingredient?> {
            return arrayOfNulls(size)
        }
    }
}

data class Step(
        @SerializedName("id")
        var id: Int,
        @SerializedName("shortDescription")
        var shortDescription: String,
        @SerializedName("description")
        var description: String,
        @SerializedName("thumbnailURL")
        var thumbnailUrl: String,
        @SerializedName("videoURL")
        var videoUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(shortDescription)
        parcel.writeString(description)
        parcel.writeString(thumbnailUrl)
        parcel.writeString(videoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Step> {
        override fun createFromParcel(parcel: Parcel): Step {
            return Step(parcel)
        }

        override fun newArray(size: Int): Array<Step?> {
            return arrayOfNulls(size)
        }
    }
}