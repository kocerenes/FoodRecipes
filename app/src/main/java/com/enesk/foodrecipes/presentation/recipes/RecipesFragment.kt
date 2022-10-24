package com.enesk.foodrecipes.presentation.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesk.foodrecipes.R
import com.enesk.foodrecipes.databinding.FragmentRecipesBinding
import com.enesk.foodrecipes.util.NetworkListener
import com.enesk.foodrecipes.util.NetworkResult
import com.enesk.foodrecipes.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<RecipesFragmentArgs>()

    private val recipesAdapter by lazy { RecipesAdapter() }
    private val recipesViewModel by viewModels<RecipesViewModel>()

    private lateinit var networkListener: NetworkListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        // for recipesBinding
        binding.lifecycleOwner = this
        binding.recipesViewModel = recipesViewModel

        setupRecyclerView()
        observeBackOnline()

        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        clickTheFabRecipes()

        return binding.root
    }

    private fun observeBackOnline() {
        recipesViewModel.readBackOnline.observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }
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
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    Log.d("RecipesFragment", "readDatabase called")
                    recipesAdapter.setData(database.first().foodRecipe)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called")
        recipesViewModel.getRecipes(recipesViewModel.applyQueries())
        recipesViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    response.data?.let {
                        recipesAdapter.setData(newData = it)
                    }
                    binding.recipesRecyclerView.visibility = View.VISIBLE
                    binding.fabRecipes.visibility = View.VISIBLE
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
                    binding.fabRecipes.visibility = View.GONE
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

    private fun searchApiData(searchQuery: String) {
        recipesViewModel.searchRecipe(recipesViewModel.applySearchQuery(searchQuery = searchQuery))
        recipesViewModel.searchRecipeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBarRecipe.visibility = View.GONE
                    val foodRecipe = response.data
                    foodRecipe?.let {
                        recipesAdapter.setData(it)
                    }
                    binding.recipesRecyclerView.visibility = View.VISIBLE
                    binding.fabRecipes.visibility = View.VISIBLE
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
                    binding.fabRecipes.visibility = View.GONE
                    binding.progressBarRecipe.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun clickTheFabRecipes() {
        binding.fabRecipes.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.searchMenu)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchApiData(query)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}