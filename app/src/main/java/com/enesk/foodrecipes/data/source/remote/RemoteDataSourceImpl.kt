package com.enesk.foodrecipes.data.source.remote

import com.enesk.foodrecipes.data.source.network.FoodRecipesApi
import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) : RemoteDataSource {

    override suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        foodRecipesApi.getRecipes(queries = queries)

    override suspend fun searchRecipe(searchQuery: Map<String, String>): Response<FoodRecipe> =
        foodRecipesApi.searchRecipes(searchQuery = searchQuery)

    override suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return foodRecipesApi.getFoodJoke(apiKey = apiKey)
    }
}