package com.example.recipefinder.features.recipeDetails.domain.useCases

import com.example.recipefinder.core.domain.repository.RecipeRepository
import com.example.recipefinder.core.domain.model.Recipe
import javax.inject.Inject

class GetFoundRecipeDetailsUseCase @Inject constructor(
    private val repository: RecipeRepository
) {

    suspend fun execute(recipeId: Int): Recipe = repository.getFoundRecipeDetails(recipeId)
}