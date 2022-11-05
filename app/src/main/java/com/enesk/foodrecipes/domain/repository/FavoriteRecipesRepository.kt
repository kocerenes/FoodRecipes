package com.enesk.foodrecipes.domain.repository

import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteRecipesRepository {

    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteAllFavoriteRecipes()
}