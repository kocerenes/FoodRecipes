package com.enesk.foodrecipes.presentation.main_screens.favorite_recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.domain.repository.FavoriteRecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteRecipesViewModel @Inject constructor(
    private val repository: FavoriteRecipesRepository
) : ViewModel() {

    var favoriteRecipeSize = 0

    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> =
        repository.readFavoriteRecipes().asLiveData()

    fun getFavoriteRecipeSize() {
        favoriteRecipeSize = readFavoriteRecipes.value?.size!!
    }

    fun insertFavoriteRecipe(favoritesEntity: FavoritesEntity) = viewModelScope.launch {
        repository.insertFavoriteRecipe(favoritesEntity)
    }

    fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity) = viewModelScope.launch {
        repository.deleteFavoriteRecipe(favoritesEntity)
    }

    fun deleteAllFavoriteRecipes() = viewModelScope.launch {
        repository.deleteAllFavoriteRecipes()
    }
}