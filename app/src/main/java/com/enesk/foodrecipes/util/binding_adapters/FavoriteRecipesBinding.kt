package com.enesk.foodrecipes.util.binding_adapters

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.presentation.main_screens.favorite_recipes.FavoriteRecipesAdapter

class FavoriteRecipesBinding {

    companion object {

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
            favoritesAdapter: FavoriteRecipesAdapter?
        ) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favoritesEntity.isNullOrEmpty()
                    view.isInvisible = dataCheck
                    if (!dataCheck) {
                        favoritesEntity?.let { favoritesAdapter?.setData(it) }
                    }
                }
                else -> view.isVisible = favoritesEntity.isNullOrEmpty()
            }
        }
    }
}