package com.enesk.foodrecipes.data.source.remote

import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>
    suspend fun searchRecipe(searchQuery: Map<String, String>): Response<FoodRecipe>
    suspend fun getFoodJoke(): Response<FoodJoke>
}