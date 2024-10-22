package com.example.recipefinder.features.favouritesRecipes.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.core.domain.model.ShortRecipe
import com.example.recipefinder.features.favouritesRecipes.domain.useCases.GetFavoriteRecipesUseCase
import com.example.recipefinder.features.recipeDetails.presentation.fragment.RecipeDetailsFragment
import com.example.recipefinder.utils.recipeType.RecipeType
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteRecipesViewModel @Inject constructor(
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
) : ViewModel() {

    @Inject
    lateinit var router: Router

    private val _favouriteRecipes = MutableStateFlow<List<ShortRecipe>>(emptyList())
    val favouriteRecipes: StateFlow<List<ShortRecipe>> = _favouriteRecipes

    init {
        refreshFavoriteRecipes()
    }

    private fun refreshFavoriteRecipes() {
        viewModelScope.launch {
            getFavoriteRecipesUseCase.execute()
                .collectLatest { recipeList ->
                    _favouriteRecipes.update { recipeList }
                }
        }
    }

    fun navigateToDetails(recipeId: Int, recipeType: RecipeType) {
        router.navigateTo(FragmentScreen {
            RecipeDetailsFragment.newInstance(recipeId, recipeType)
        })
    }
}