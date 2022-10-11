package com.enesk.foodrecipes.data.repository

import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import com.enesk.foodrecipes.data.source.remote.RemoteDataSource
import com.enesk.foodrecipes.domain.repository.RecipesRepository
import retrofit2.Response
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : RecipesRepository {

    override suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        remoteDataSource.getRecipes(queries = queries)
}