package com.enesk.foodrecipes.di

import android.content.Context
import androidx.room.Room
import com.enesk.foodrecipes.data.source.database.RecipesDatabase
import com.enesk.foodrecipes.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRecipesDatabase(
        context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideRecipesDao(database: RecipesDatabase) = database.recipesDao()
}