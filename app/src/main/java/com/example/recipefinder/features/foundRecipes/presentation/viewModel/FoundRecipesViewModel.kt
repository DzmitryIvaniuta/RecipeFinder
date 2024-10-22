package com.example.recipefinder.features.foundRecipes.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipefinder.core.domain.model.ShortRecipe
import com.example.recipefinder.features.favouritesRecipes.presentation.fragment.FavouriteRecipesFragment
import com.example.recipefinder.features.foundRecipes.domain.useCases.SearchRecipesUseCase
import com.example.recipefinder.features.recipeDetails.presentation.fragment.RecipeDetailsFragment
import com.example.recipefinder.utils.recipeType.RecipeType
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoundRecipesViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val router: Router
): ViewModel() {

    private val _foundRecipes = MutableStateFlow<PagingData<ShortRecipe>>(PagingData.empty())
    val foundRecipes: StateFlow<PagingData<ShortRecipe>> = _foundRecipes

    private val queryFlow = MutableStateFlow("")

    init {
        handleSearchQuery()
    }

    fun updateSearchQuery(query: String) {
        queryFlow.value = query
    }

    @OptIn(FlowPreview::class)
    private fun handleSearchQuery() {
        viewModelScope.launch {
            queryFlow
                .debounce(500L)
                .filter { it.length >= 3 }
                .distinctUntilChanged()
                .collectLatest { query ->
                    searchRecipes(query)
                }
        }
    }

    private fun searchRecipes(query: String) {
        viewModelScope.launch {
            searchRecipesUseCase.execute(query)
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _foundRecipes.value = pagingData
                }
        }
    }

    fun navigateToDetails(recipeId: Int, recipeType: RecipeType) {
        router.navigateTo(FragmentScreen {
            RecipeDetailsFragment.newInstance(recipeId, recipeType)
        })
    }
}
