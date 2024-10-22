package com.example.recipefinder.features.favouritesRecipes.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefinder.R
import com.example.recipefinder.app.presentation.App
import com.example.recipefinder.features.favouritesRecipes.presentation.adapter.FavouriteRecipesAdapter
import com.example.recipefinder.databinding.FragmentFavouriteRecipesBinding
import com.example.recipefinder.features.favouritesRecipes.presentation.viewModel.FavouriteRecipesViewModel
import com.example.recipefinder.utils.recipeType.RecipeType
import com.example.recipefinder.utils.imageLoader.ImageLoader
import com.example.recipefinder.utils.viewBinding.fragmentViewBinding.viewBinding
import com.example.recipefinder.utils.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteRecipesFragment : Fragment(R.layout.fragment_favourite_recipes) {

    private val binding by viewBinding(FragmentFavouriteRecipesBinding::bind)

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val favouriteRecipesViewModel by viewModels<FavouriteRecipesViewModel> { viewModelFactory }

    private val recipesAdapter by lazy {
        FavouriteRecipesAdapter(imageLoader) { recipeId -> actionAfterClickOnItem(recipeId) }
    }

    private fun actionAfterClickOnItem(recipeId: Int) {
        favouriteRecipesViewModel.navigateToDetails(recipeId, RecipeType.FAVOURITE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as? App)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
            ?: throw IllegalStateException("View cannot be null")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.apply {
            favoriteRecyclerView.layoutManager = LinearLayoutManager(context)
            favoriteRecyclerView.adapter = recipesAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                favouriteRecipesViewModel.favouriteRecipes
                    .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                    .collect { recipesList ->
                        recipesAdapter.items = recipesList
                    }
            }
        }
    }
}