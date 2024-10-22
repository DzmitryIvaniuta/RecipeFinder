package com.example.recipefinder.utils.recipeState

import com.example.recipefinder.core.domain.model.Recipe

sealed class RecipeState {
    data object Loading : RecipeState()
    data class Success(val recipe: Recipe) : RecipeState()
    data object Error : RecipeState()
}
