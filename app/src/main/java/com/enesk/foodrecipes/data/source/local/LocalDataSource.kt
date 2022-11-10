package com.enesk.foodrecipes.data.source.local

import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.data.source.database.entity.FoodJokeEntity
import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    /** Recipes **/
    fun readDatabase(): Flow<List<RecipesEntity>>
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    /** Favorite Recipes **/
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>
    suspend fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)
    suspend fun deleteAllFavoriteRecipes()

    /** Food Joke **/
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)
}