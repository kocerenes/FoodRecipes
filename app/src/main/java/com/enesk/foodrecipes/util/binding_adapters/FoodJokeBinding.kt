package com.enesk.foodrecipes.util.binding_adapters

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.enesk.foodrecipes.data.source.database.entity.FoodJokeEntity
import com.enesk.foodrecipes.data.source.network.model.food_joke.FoodJoke
import com.enesk.foodrecipes.util.NetworkResult
import com.google.android.material.card.MaterialCardView

class FoodJokeBinding {

    companion object {

        @BindingAdapter("readApiResponseForFoodJoke", "readDatabaseForFoodJoke", requireAll = false)
        @JvmStatic
        fun setCardAndProgressVisibility(
            view: View,
            apiResponse: NetworkResult<FoodJoke>?,
            database: List<FoodJokeEntity>?
        ) {
            when (apiResponse) {
                is NetworkResult.Loading -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.VISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.INVISIBLE
                        }
                    }
                }
                is NetworkResult.Error -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                            if (database != null && database.isEmpty()) {
                                view.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
                is NetworkResult.Success -> {
                    when (view) {
                        is ProgressBar -> {
                            view.visibility = View.INVISIBLE
                        }
                        is MaterialCardView -> {
                            view.visibility = View.VISIBLE
                        }
                    }
                }
                else -> {}
            }
        }
    }
}