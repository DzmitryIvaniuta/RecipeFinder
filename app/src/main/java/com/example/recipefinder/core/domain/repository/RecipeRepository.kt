package com.example.recipefinder.core.domain.repository

import androidx.paging.PagingData
import com.example.recipefinder.core.domain.model.Recipe
import com.example.recipefinder.core.domain.model.ShortRecipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun searchRecipes(query: String): Flow<PagingData<ShortRecipe>>
    suspend fun getFavoriteRecipes(): Flow<List<ShortRecipe>>
    suspend fun getFoundRecipeDetails(recipeId: Int): Recipe
    suspend fun saveRecipeToFavorites(recipe: Recipe)
    suspend fun getFavoriteRecipeDetails(recipeId: Int): Recipe
    suspend fun deleteRecipe(recipeId: Int)
}