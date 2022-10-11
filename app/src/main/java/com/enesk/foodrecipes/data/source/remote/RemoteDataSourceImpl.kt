package com.enesk.foodrecipes.data.source.remote

import com.enesk.foodrecipes.data.source.network.FoodRecipesApi
import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) : RemoteDataSource {

    override suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        foodRecipesApi.getRecipes(queries = queries)
}