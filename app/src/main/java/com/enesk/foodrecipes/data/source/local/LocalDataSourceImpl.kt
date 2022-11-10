package com.enesk.foodrecipes.data.source.local

import com.enesk.foodrecipes.data.source.database.RecipesDao
import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.data.source.database.entity.FoodJokeEntity
import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : LocalDataSource {

    /** Recipes **/
    override fun readDatabase(): Flow<List<RecipesEntity>> =
        recipesDao.readRecipes()

    override suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity = recipesEntity)
    }

    /** Favorite Recipes **/
    override fun readFavoriteRecipes(): Flow<List<FavoritesEntity>> {
        return recipesDao.readFavoriteRecipes()
    }

    override suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.insertFavoriteRecipe(favoritesEntity = favoritesEntity)
    }

    override suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) {
        recipesDao.deleteFavoriteRecipe(favoritesEntity = favoritesEntity)
    }

    override suspend fun deleteAllFavoriteRecipes() {
        recipesDao.deleteAllFavoriteRecipes()
    }

    /** Food Joke **/
    override fun readFoodJoke(): Flow<List<FoodJokeEntity>> = recipesDao.readFoodJoke()
    override suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity = foodJokeEntity)
    }
}