package com.enesk.foodrecipes.presentation.main_screens.food_joke

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.enesk.foodrecipes.data.source.database.entity.FoodJokeEntity
import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import com.enesk.foodrecipes.domain.repository.FoodJokeRepository
import com.enesk.foodrecipes.util.NetworkResult
import dagger.hilt.android.internal.Contexts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FoodJokeViewModel @Inject constructor(
    private val repository: FoodJokeRepository,
    private val context: Context,
) : ViewModel() {

    var foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()
    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.readFoodJoke().asLiveData()

    fun getFoodJoke() = viewModelScope.launch {
        getFoodJokeSafeCall()
    }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) = viewModelScope.launch {
        repository.insertFoodJoke(foodJokeEntity)
    }

    private suspend fun getFoodJokeSafeCall() {
        foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.getFoodJoke()
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeResponse.value?.data
                if (foodJoke != null) {
                    offlineCacheFoodJoke(foodJoke = foodJoke)
                }
            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            foodJokeResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = Contexts.getApplication(context).getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API key limited.")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()
                NetworkResult.Success(data = foodJoke!!)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    /** ROOM */
    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity = foodJokeEntity)
    }
}