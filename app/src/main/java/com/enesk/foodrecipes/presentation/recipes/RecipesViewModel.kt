package com.enesk.foodrecipes.presentation.recipes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.enesk.foodrecipes.data.data_store.DataStoreRepository
import com.enesk.foodrecipes.data.source.database.entity.RecipesEntity
import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import com.enesk.foodrecipes.domain.repository.RecipesRepository
import com.enesk.foodrecipes.util.Constants
import com.enesk.foodrecipes.util.Constants.DEFAULT_DIET_TYPE
import com.enesk.foodrecipes.util.Constants.DEFAULT_MEAL_TYPE
import com.enesk.foodrecipes.util.NetworkResult
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val repository: RecipesRepository,
    private val context: Context,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    /** DATA_STORE */
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    /** ROOM */
    val readRecipes: LiveData<List<RecipesEntity>> = repository.readDatabase().asLiveData()

    /** RETROFIT */
    private val _recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val recipesResponse: LiveData<NetworkResult<FoodRecipe>> = _recipesResponse

    /** RETROFIT */
    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.getRecipes(queries = queries)
                _recipesResponse.value = handleFoodRecipesResponse(response = response)

                val foodRecipe = _recipesResponse.value!!.data
                if (foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                _recipesResponse.value = NetworkResult.Error(message = e.message)
            }
        } else {
            _recipesResponse.value = NetworkResult.Error(message = "No Internet Connection.")
        }
    }


    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val foodRecipes = response.body()
                return NetworkResult.Success(data = foodRecipes!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication(context).getSystemService(
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

    /** ROOM */
    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity = recipesEntity)
    }

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecipes(recipesEntity = recipesEntity)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { type ->
                mealType = type.selectedMealType
                dietType = type.selectedDietType
            }
        }

        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}