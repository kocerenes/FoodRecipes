package com.enesk.foodrecipes.util.binding_adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.enesk.foodrecipes.data.source.database.entity.FavoritesEntity
import com.enesk.foodrecipes.presentation.favorite_recipes.FavoriteRecipesAdapter

class FavoriteRecipesBinding {

    companion object {

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoriteEntities: List<FavoritesEntity>?,
            favoritesAdapter: FavoriteRecipesAdapter?
        ) {
            if (favoriteEntities.isNullOrEmpty()) {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            } else {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        if (favoriteEntities != null) {
                            favoritesAdapter?.setData(favoriteEntities)
                        }
                    }
                }
            }
        }
    }
}