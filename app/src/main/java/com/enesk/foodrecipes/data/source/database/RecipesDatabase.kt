package com.enesk.foodrecipes.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.data.source.database.entity.FoodJokeEntity
import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import com.enesk.foodrecipes.data.util.RecipesTypeConverter

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}