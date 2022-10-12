package com.enesk.foodrecipes.presentation.recipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enesk.foodrecipes.data.source.network.model.FoodRecipe
import com.enesk.foodrecipes.data.source.network.model.Result
import com.enesk.foodrecipes.databinding.RecipesRowLayoutBinding

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipe = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder.from(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        holder.bind(result = currentResult)
    }

    override fun getItemCount(): Int = recipe.size

    fun setData(newData: FoodRecipe) {
        recipe = newData.results
        notifyDataSetChanged()
    }
}