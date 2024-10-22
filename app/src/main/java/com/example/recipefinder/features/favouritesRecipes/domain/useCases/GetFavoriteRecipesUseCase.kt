package com.example.recipefinder.features.favouritesRecipes.domain.useCases

import com.example.recipefinder.core.domain.repository.RecipeRepository
import com.example.recipefinder.core.domain.model.ShortRecipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend fun execute(): Flow<List<ShortRecipe>> = repository.getFavoriteRecipes()
}