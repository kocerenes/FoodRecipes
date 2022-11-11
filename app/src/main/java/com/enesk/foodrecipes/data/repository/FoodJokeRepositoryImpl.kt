package com.enesk.foodrecipes.data.repository

import com.enesk.foodrecipes.data.source.database.entity.FoodJokeEntity
import com.enesk.foodrecipes.data.source.local.LocalDataSource
import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import com.enesk.foodrecipes.data.source.remote.RemoteDataSource
import com.enesk.foodrecipes.domain.repository.FoodJokeRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class FoodJokeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : FoodJokeRepository {

    override suspend fun getFoodJoke(): Response<FoodJoke> {
        return remoteDataSource.getFoodJoke()
    }

    override fun readFoodJoke(): Flow<List<FoodJokeEntity>> =
        localDataSource.readFoodJoke()

    override suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        localDataSource.insertFoodJoke(foodJokeEntity = foodJokeEntity)
    }
}