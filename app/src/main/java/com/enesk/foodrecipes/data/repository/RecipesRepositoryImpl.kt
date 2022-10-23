package com.enesk.foodrecipes.data.repository

import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import com.enesk.foodrecipes.data.source.local.LocalDataSource
import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import com.enesk.foodrecipes.data.source.remote.RemoteDataSource
import com.enesk.foodrecipes.domain.repository.RecipesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RecipesRepository {

    override suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> =
        remoteDataSource.getRecipes(queries = queries)

    override fun readDatabase(): Flow<List<RecipesEntity>> =
        localDataSource.readDatabase()

    override suspend fun insertRecipes(recipesEntity: RecipesEntity) =
        localDataSource.insertRecipes(recipesEntity)

    override suspend fun searchRecipe(searchQuery: Map<String, String>): Response<FoodRecipe> =
        remoteDataSource.searchRecipe(searchQuery = searchQuery)
}