package com.example.recipefinder.features.recipeDetails.domain.useCases

import com.example.recipefinder.core.domain.repository.RecipeRepository
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {

    suspend fun execute(recipeId: Int) = repository.deleteRecipe(recipeId)
}