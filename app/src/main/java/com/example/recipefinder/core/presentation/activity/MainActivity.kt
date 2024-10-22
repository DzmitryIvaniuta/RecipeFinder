package com.example.recipefinder.core.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recipefinder.R
import com.example.recipefinder.app.presentation.App
import com.example.recipefinder.databinding.ActivityMainBinding
import com.example.recipefinder.features.favouritesRecipes.presentation.fragment.FavouriteRecipesFragment
import com.example.recipefinder.features.foundRecipes.presentation.fragment.FoundRecipesFragment
import com.example.recipefinder.utils.viewBinding.activityViewBinding.viewBinding
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.fragmentContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        (application as App).appComponent.inject(this)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragmentToShow = when (item.itemId) {
                R.id.home -> FoundRecipesFragment()
                R.id.favourites -> FavouriteRecipesFragment()
                else -> return@setOnItemSelectedListener false
            }

            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

            if (currentFragment == null || currentFragment::class != fragmentToShow::class) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragmentToShow)
                    .commit()
            }
            true
        }

        if (savedInstanceState == null) {
            router.newRootScreen(FragmentScreen { FoundRecipesFragment() })
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}