package com.enesk.foodrecipes.presentation.favorite_recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesk.foodrecipes.R
import com.enesk.foodrecipes.databinding.FragmentFavoriteRecipesBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment(), MenuProvider {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val favoriteViewModel by viewModels<FavoriteRecipesViewModel>()
    private val favoriteAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(requireActivity(), favoriteViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.favoriteViewModel = favoriteViewModel
        binding.favoriteAdapter = favoriteAdapter

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.favoriteRecipeRecyclerView.adapter = favoriteAdapter
        binding.favoriteRecipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.favorite_recipes_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.delete_all_favorite_recipes_menu) {
            favoriteViewModel.getFavoriteRecipeSize()
            if (favoriteViewModel.favoriteRecipeSize > 0) {
                favoriteViewModel.deleteAllFavoriteRecipes()
                showSnackBar("All recipes removed.")
            } else {
                showSnackBar("No recipes in favorites.")
            }
        }
        return true
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        favoriteAdapter.clearContextualActionMode()
    }
}