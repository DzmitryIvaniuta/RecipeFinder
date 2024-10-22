package com.example.recipefinder.features.foundRecipes.domain.useCases

import androidx.paging.PagingData
import com.example.recipefinder.core.domain.repository.RecipeRepository
import com.example.recipefinder.core.domain.model.ShortRecipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {

    suspend fun execute(query: String): Flow<PagingData<ShortRecipe>> = repository.searchRecipes(query)
}