package com.enesk.foodrecipes.data.source.local

import com.enesk.foodrecipes.data.source.database.RecipesDao
import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val recipesDao: RecipesDao
) : LocalDataSource {
    override fun readDatabase(): Flow<List<RecipesEntity>> =
        recipesDao.readRecipes()

    override suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity = recipesEntity)
    }
}