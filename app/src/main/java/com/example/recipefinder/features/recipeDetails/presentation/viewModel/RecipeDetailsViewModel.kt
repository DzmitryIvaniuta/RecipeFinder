package com.example.recipefinder.features.recipeDetails.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinder.core.domain.model.Recipe
import com.example.recipefinder.features.recipeDetails.domain.useCases.DeleteRecipeUseCase
import com.example.recipefinder.features.recipeDetails.domain.useCases.GetFavoriteRecipeDetailsUseCase
import com.example.recipefinder.features.recipeDetails.domain.useCases.GetFoundRecipeDetailsUseCase
import com.example.recipefinder.features.recipeDetails.domain.useCases.SaveRecipeToFavoritesUseCase
import com.example.recipefinder.utils.recipeState.RecipeState
import com.example.recipefinder.utils.recipeType.RecipeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailsViewModel @Inject constructor(
    private val getFoundRecipeDetailsUseCase: GetFoundRecipeDetailsUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val getFavoriteRecipeDetailsUseCase: GetFavoriteRecipeDetailsUseCase,
    private val saveRecipeToFavoritesUseCase: SaveRecipeToFavoritesUseCase,
): ViewModel() {

    private val _foundRecipe = MutableStateFlow<RecipeState>(RecipeState.Loading)
    private val foundRecipe: StateFlow<RecipeState> = _foundRecipe

    private val _favouriteRecipe = MutableStateFlow<RecipeState>(RecipeState.Loading)
    private val favouriteRecipe: StateFlow<RecipeState> = _favouriteRecipe

    private val _recipeFlow = MutableStateFlow<RecipeState>(RecipeState.Loading)
    val recipeFlow: StateFlow<RecipeState> = _recipeFlow

    fun getFoundRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            _foundRecipe.value = RecipeState.Loading
            val recipe = getFoundRecipeDetailsUseCase.execute(recipeId)
            _foundRecipe.value = RecipeState.Success(recipe)
        }
    }

    fun getFavouriteRecipeDetails(recipeId: Int) {
        viewModelScope.launch {
            _favouriteRecipe.value = RecipeState.Loading
            val recipe = getFavoriteRecipeDetailsUseCase.execute(recipeId)
            _favouriteRecipe.value = RecipeState.Success(recipe)
        }
    }

    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch {
            saveRecipeToFavoritesUseCase.execute(recipe)
        }
    }

    fun deleteRecipe(recipeId: Int) {
        viewModelScope.launch {
            deleteRecipeUseCase.execute(recipeId)
        }
    }

    fun setRecipeFlow(recipeType: RecipeType) {
        when (recipeType) {
            RecipeType.FOUND -> {
                viewModelScope.launch {
                    foundRecipe.collect { recipe ->
                        _recipeFlow.value = recipe
                    }
                }
            }
            RecipeType.FAVOURITE -> {
                viewModelScope.launch {
                    favouriteRecipe.collect { recipe ->
                        _recipeFlow.value = recipe
                    }
                }
            }
        }
    }
}