package com.example.recipefinder.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.recipefinder.core.data.network.paging.RecipePagingSource
import com.example.recipefinder.core.data.network.retrofit.SpoonacularApi
import com.example.recipefinder.core.data.storage.room.RecipeDatabase
import com.example.recipefinder.core.domain.model.Recipe
import com.example.recipefinder.core.domain.model.ShortRecipe
import com.example.recipefinder.core.domain.repository.RecipeRepository
import com.example.recipefinder.utils.mapper.entityToIngredient
import com.example.recipefinder.utils.mapper.responseToRecipe
import com.example.recipefinder.utils.mapper.entityToRecipe
import com.example.recipefinder.utils.mapper.domainToIngredientEntity
import com.example.recipefinder.utils.mapper.domainToRecipeEntity
import com.example.recipefinder.utils.mapper.entityToShortRecipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDatabase: RecipeDatabase,
    private val recipeApi: SpoonacularApi
) : RecipeRepository {
    override suspend fun searchRecipes(query: String): Flow<PagingData<ShortRecipe>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        pagingSourceFactory = { RecipePagingSource(recipeApi, query) }
    ).flow

    override suspend fun getFavoriteRecipes(): Flow<List<ShortRecipe>> {
        val favoriteRecipes = recipeDatabase.getRecipeDao().getAllRecipes()
        return favoriteRecipes.map { recipeEntities -> recipeEntities.map { it.entityToShortRecipe() } }
    }

    override suspend fun getFoundRecipeDetails(recipeId: Int): Recipe {
        val recipeResponse = recipeApi.getRecipeDetails(recipeId)
        return recipeResponse.responseToRecipe()
    }

    override suspend fun saveRecipeToFavorites(recipe: Recipe) {
        val recipeEntity = recipe.domainToRecipeEntity()
        recipeDatabase.getRecipeDao().insertRecipeWithIngredients(recipeEntity, recipe.ingredients.map {
            it.domainToIngredientEntity(0)
        })
    }

    override suspend fun getFavoriteRecipeDetails(recipeId: Int): Recipe {
        val recipeEntity = recipeDatabase.getRecipeDao().getRecipeById(recipeId)
        val ingredientEntities = recipeDatabase.getRecipeDao().getIngredientsForRecipe(recipeId)

        return recipeEntity.entityToRecipe(
            ingredients = ingredientEntities.map { it.entityToIngredient() }
        )
    }

    override suspend fun deleteRecipe(recipeId: Int) {
        recipeDatabase.getRecipeDao().deleteRecipeById(recipeId)
        recipeDatabase.getRecipeDao().deleteIngredientsForRecipe(recipeId)
    }
}
