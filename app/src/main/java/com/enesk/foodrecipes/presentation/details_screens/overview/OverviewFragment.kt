package com.enesk.foodrecipes.presentation.details_screens.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.enesk.foodrecipes.R
import com.enesk.foodrecipes.data.source.network.model.Result
import com.enesk.foodrecipes.databinding.FragmentOverviewBinding
import com.enesk.foodrecipes.util.Constants.RECIPE_RESULT_KEY
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {

    private lateinit var _binding: FragmentOverviewBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        displayDataOnScreen(myBundle = myBundle)

        return binding.root
    }

    private fun displayDataOnScreen(myBundle: Result?) {
        myBundle?.let { result ->
            with(binding) {
                mainImageView.load(result.image)
                titleTextView.text = result.title
                likesTextView.text = result.aggregateLikes.toString()
                timeTextView.text = result.readyInMinutes.toString()
                result?.summary.let {
                    val summary = Jsoup.parse(it).text()
                    summaryTextView.text = summary
                }

                result.vegetarian?.let {
                    setColorOfIcon(it, vegetarianImageView, vegetarianTextView)
                }
                result.vegan?.let { setColorOfIcon(it, veganImageView, veganTextView) }
                result.glutenFree?.let {
                    setColorOfIcon(it, glutenFreeImageView, glutenFreeTextView)
                }
                result.dairyFree?.let { setColorOfIcon(it, dairyFreeImageView, dairyFreeTextView) }
                result.veryHealthy?.let { setColorOfIcon(it, healthyImageView, healthyTextView) }
                result.cheap?.let { setColorOfIcon(it, cheapImageView, cheapTextView) }
            }
        }
    }

    private fun setColorOfIcon(result: Boolean, imageView: ImageView, textView: TextView) {
        if (result) {
            imageView.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
    }
}