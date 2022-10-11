package com.enesk.foodrecipes.domain.repository

import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import retrofit2.Response

interface RecipesRepository {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>
}