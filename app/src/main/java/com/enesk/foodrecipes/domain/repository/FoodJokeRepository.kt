package com.enesk.foodrecipes.domain.repository

import com.enesk.foodrecipes.data.source.database.entity.FoodJokeEntity
import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FoodJokeRepository {

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke>
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)
}