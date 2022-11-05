package com.enesk.foodrecipes.data.repository

import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.data.source.local.LocalDataSource
import com.enesk.foodrecipes.domain.repository.FavoriteRecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRecipesRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource
) : FavoriteRecipesRepository {

    override fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return localDataSource.readFavoriteRecipes()
    }

    override suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        localDataSource.insertFavoriteRecipe(favoritesEntity)
    }

    override suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        localDataSource.deleteFavoriteRecipe(favoritesEntity)
    }

    override suspend fun deleteAllFavoriteRecipes() {
        localDataSource.deleteAllFavoriteRecipes()
    }
}