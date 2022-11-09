package com.enesk.foodrecipes.presentation.favorite_recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.enesk.foodrecipes.databinding.FragmentFavoriteRecipesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {

    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val favoriteAdapter: FavoriteRecipesAdapter by lazy {
        FavoriteRecipesAdapter(requireActivity())
    }
    private val favoriteViewModel by viewModels<FavoriteRecipesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.favoriteViewModel = favoriteViewModel
        binding.favoriteAdapter = favoriteAdapter

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.favoriteRecipeRecyclerView.adapter = favoriteAdapter
        binding.favoriteRecipeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}