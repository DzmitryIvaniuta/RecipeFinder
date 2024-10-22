package com.example.recipefinder.utils.mapper

import androidx.core.text.HtmlCompat
import com.example.recipefinder.core.data.network.models.detailsResponseModel.RecipeDetailResponseModel
import com.example.recipefinder.core.data.storage.model.RecipeEntity
import com.example.recipefinder.core.domain.model.Ingredient
import com.example.recipefinder.core.domain.model.Recipe

fun RecipeEntity.entityToRecipe(ingredients: List<Ingredient>): Recipe {
    return Recipe(
        id = this.id,
        title = this.title,
        image = this.image,
        readyInMinutes = this.readyInMinutes,
        instructions = this.instructions,
        ingredients = ingredients
    )
}

fun RecipeDetailResponseModel.responseToRecipe(): Recipe {
    return Recipe(
        id = this.id ?: throw IllegalArgumentException("Recipe ID is missing"),
        title = this.title ?: "Unknown Recipe",
        image = this.image ?: "",
        readyInMinutes = this.readyInMinutes ?: 0,
        instructions = HtmlCompat.fromHtml(
            this.instructions ?: "No instructions available",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString(),
        ingredients = this.ingredients?.map { it.responseToIngredient() } ?: emptyList()
    )
}