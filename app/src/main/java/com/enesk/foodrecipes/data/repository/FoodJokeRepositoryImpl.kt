package com.enesk.foodrecipes.data.repository

import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import com.enesk.foodrecipes.data.source.remote.RemoteDataSource
import com.enesk.foodrecipes.domain.repository.FoodJokeRepository
import retrofit2.Response
import javax.inject.Inject

class FoodJokeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : FoodJokeRepository {

    override suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return remoteDataSource.getFoodJoke(apiKey = apiKey)
    }
}