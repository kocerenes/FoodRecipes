package com.enesk.foodrecipes.presentation.details_screens.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.enesk.foodrecipes.data.source.network.model.Result
import com.enesk.foodrecipes.databinding.FragmentInstructionsBinding
import com.enesk.foodrecipes.util.Constants.RECIPE_RESULT_KEY

class InstructionsFragment : Fragment() {

    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupWebView(myBundle = myBundle)

        return binding.root
    }

    private fun setupWebView(myBundle: Result?) {
        with(binding) {
            instructionsWebView.webViewClient = object : WebViewClient() {}
            val websiteUrl = myBundle?.sourceUrl
            websiteUrl?.let { instructionsWebView.loadUrl(websiteUrl) }
        }
    }
}