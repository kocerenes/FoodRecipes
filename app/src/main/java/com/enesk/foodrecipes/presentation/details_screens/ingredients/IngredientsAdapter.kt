package com.enesk.foodrecipes.presentation.details_screens.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.enesk.foodrecipes.R
import com.enesk.foodrecipes.data.source.network.model.ExtendedIngredient
import com.enesk.foodrecipes.databinding.IngredientsRowLayoutBinding
import com.enesk.foodrecipes.util.Constants.BASE_IMAGE_URL
import com.enesk.foodrecipes.util.RecipesDiffUtil
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = IngredientsRowLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {
            with(ingredientsList[position]) {
                binding.ingredientImageView.load(BASE_IMAGE_URL + image) {
                    crossfade(600)
                    error(R.drawable.ic_error_placeholder)
                }
                name?.let {
                    binding.ingredientNameTextView.text =
                        it.replaceFirstChar {
                            if (it.isLowerCase()) {
                                it.titlecase(Locale.ROOT)
                            } else {
                                it.toString()
                            }
                        }
                }
                binding.ingredientAmountTextView.text = amount.toString()
                binding.ingredientUnitTextView.text = unit
                binding.ingredientConsistencyTextView.text = consistency
                binding.ingredientOriginalTextView.text = original
            }
        }
    }

    override fun getItemCount(): Int = ingredientsList.size

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil = RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)

    }
}