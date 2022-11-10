package com.enesk.foodrecipes.data.source.network.model.food_joke


import com.google.gson.annotations.SerializedName

data class FoodJoke(
    @SerializedName("text")
    val text: String?
)