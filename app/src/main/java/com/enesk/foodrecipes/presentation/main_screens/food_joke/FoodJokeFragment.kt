package com.enesk.foodrecipes.presentation.main_screens.food_joke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.enesk.foodrecipes.R
import com.enesk.foodrecipes.databinding.FragmentFoodJokeBinding
import com.enesk.foodrecipes.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoodJokeFragment : Fragment(), MenuProvider {

    private val foodJokeViewModel by viewModels<FoodJokeViewModel>()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var sharedFoodJoke = "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.foodJokeViewModel = foodJokeViewModel

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        observeFoodJoke()

        return binding.root
    }

    private fun observeFoodJoke() {
        foodJokeViewModel.getFoodJoke()
        foodJokeViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.foodJokeTextView.text = response.data?.text
                    sharedFoodJoke = response.data?.text.toString()
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
                    sharedFoodJoke = listFoodJoke[0].foodJoke.text.toString()
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.share_food_joke_menu) {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, sharedFoodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}