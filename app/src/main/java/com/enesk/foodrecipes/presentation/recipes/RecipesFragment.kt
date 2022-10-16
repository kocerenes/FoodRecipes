package com.enesk.foodrecipes.presentation.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesk.foodrecipes.databinding.FragmentRecipesBinding
import com.enesk.foodrecipes.util.Constants.API_KEY
import com.enesk.foodrecipes.util.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.enesk.foodrecipes.util.Constants.QUERY_API_KEY
import com.enesk.foodrecipes.util.Constants.QUERY_DIET
import com.enesk.foodrecipes.util.Constants.QUERY_FILL_INGREDIENTS
import com.enesk.foodrecipes.util.Constants.QUERY_NUMBER
import com.enesk.foodrecipes.util.Constants.QUERY_TYPE
import com.enesk.foodrecipes.util.NetworkResult
import com.enesk.foodrecipes.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val recipesAdapter by lazy { RecipesAdapter() }
    private val recipesViewModel by viewModels<RecipesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        readDatabase()

        return binding.root
    }

    private fun setupRecyclerView() {
        with(binding) {
            recipesRecyclerView.adapter = recipesAdapter
            recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            recipesViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("RecipesFragment", "readDatabase called")
                    recipesAdapter.setData(database[0].foodRecipe)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called")
        recipesViewModel.getRecipes(applyQueries())
        recipesViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    response.data?.let {
                        recipesAdapter.setData(newData = it)
                    }
                    binding.recipesRecyclerView.visibility = View.VISIBLE
                    binding.floatingActionButton.visibility = View.VISIBLE
                }
                is NetworkResult.Error -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.recipesRecyclerView.visibility = View.GONE
                    binding.floatingActionButton.visibility = View.GONE
                    binding.progressBarRecipe.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            recipesViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    recipesAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}