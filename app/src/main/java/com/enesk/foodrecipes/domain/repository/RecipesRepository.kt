package com.enesk.foodrecipes.domain.repository

import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RecipesRepository {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe>
    fun readDatabase(): Flow<List<RecipesEntity>>
    suspend fun insertRecipes(recipesEntity: RecipesEntity)
    suspend fun searchRecipe(searchQuery: Map<String, String>): Response<FoodRecipe>
}