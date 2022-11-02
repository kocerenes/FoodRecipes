package com.enesk.foodrecipes.data.source.local

import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun readDatabase(): Flow<List<RecipesEntity>>
    suspend fun insertRecipes(recipesEntity: RecipesEntity)
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteAllFavoriteRecipes()
}