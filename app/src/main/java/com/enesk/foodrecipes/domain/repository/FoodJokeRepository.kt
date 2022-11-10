package com.enesk.foodrecipes.domain.repository

import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import retrofit2.Response

interface FoodJokeRepository {

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke>
}