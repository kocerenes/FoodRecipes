package com.enesk.foodrecipes.di

import com.enesk.foodrecipes.data.repository.FavoriteRecipesRepositoryImpl
import com.enesk.foodrecipes.data.repository.FoodJokeRepositoryImpl
import com.enesk.foodrecipes.data.repository.RecipesRepositoryImpl
import com.enesk.foodrecipes.domain.repository.FavoriteRecipesRepository
import com.enesk.foodrecipes.domain.repository.FoodJokeRepository
import com.enesk.foodrecipes.domain.repository.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideRecipesRepository(
        recipesRepositoryImpl: RecipesRepositoryImpl
    ): RecipesRepository

    @Binds
    @Singleton
    abstract fun provideFavoriteRecipesRepository(
        favoriteRecipesRepositoryImpl: FavoriteRecipesRepositoryImpl
    ): FavoriteRecipesRepository

    @Binds
    @Singleton
    abstract fun provideFoodJokeRepository(
        foodJokeRepositoryImpl: FoodJokeRepositoryImpl
    ): FoodJokeRepository
}