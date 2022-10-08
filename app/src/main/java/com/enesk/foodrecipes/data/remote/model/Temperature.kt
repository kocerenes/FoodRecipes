package com.enesk.foodrecipes.data.remote.model


import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("number")
    val number: Double,
    @SerializedName("unit")
    val unit: String
)