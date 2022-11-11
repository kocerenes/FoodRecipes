package com.enesk.foodrecipes.presentation.main_screens.food_joke

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.enesk.foodrecipes.databinding.FragmentFoodJokeBinding
import com.enesk.foodrecipes.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val foodJokeViewModel by viewModels<FoodJokeViewModel>()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.foodJokeViewModel = foodJokeViewModel

        observeFoodJoke()

        return binding.root
    }

    private fun observeFoodJoke() {
        foodJokeViewModel.getFoodJoke()
        foodJokeViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.foodJokeTextView.text = response.data?.text
                }
                is NetworkResult.Loading -> {
                    Log.d("FoodJokeFragment", "Loading")
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            foodJokeViewModel.readFoodJoke.observe(viewLifecycleOwner) { listFoodJoke ->
                if (!listFoodJoke.isNullOrEmpty()) {
                    binding.foodJokeTextView.text = listFoodJoke[0].foodJoke.text
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}