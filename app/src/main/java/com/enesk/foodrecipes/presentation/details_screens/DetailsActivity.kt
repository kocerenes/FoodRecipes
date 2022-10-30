package com.enesk.foodrecipes.presentation.details_screens

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.enesk.foodrecipes.R
import com.enesk.foodrecipes.common.adapter.PagerAdapter
import com.enesk.foodrecipes.databinding.ActivityDetailsBinding
import com.enesk.foodrecipes.presentation.details_screens.ingredients.IngredientsFragment
import com.enesk.foodrecipes.presentation.details_screens.instructions.InstructionsFragment
import com.enesk.foodrecipes.presentation.details_screens.overview.OverviewFragment
import com.enesk.foodrecipes.util.Constants.RECIPE_RESULT_KEY

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle = resultBundle,
            fragments = fragments,
            title = titles,
            fragmentManager = supportFragmentManager
        )

        with(binding){
            viewPager.adapter = pagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}