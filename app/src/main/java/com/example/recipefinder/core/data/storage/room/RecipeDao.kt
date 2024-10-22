package com.example.recipefinder.core.data.storage.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.recipefinder.core.data.storage.model.IngredientEntity
import com.example.recipefinder.core.data.storage.model.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Transaction
    suspend fun insertRecipeWithIngredients(recipe: RecipeEntity, ingredients: List<IngredientEntity>) {
        insertRecipe(recipe)
        insertIngredients(ingredients.map { it.copy(recipeId = recipe.id) })
    }

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    suspend fun getRecipeById(recipeId: Int): RecipeEntity

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Int): List<IngredientEntity>

    @Query("DELETE FROM recipes WHERE id = :recipeId")
    suspend fun deleteRecipeById(recipeId: Int)

    @Query("DELETE FROM ingredients WHERE recipeId = :recipeId")
    suspend fun deleteIngredientsForRecipe(recipeId: Int)
}