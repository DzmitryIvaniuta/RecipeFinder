package com.example.recipefinder.utils.mapper

import com.example.recipefinder.core.data.storage.model.IngredientEntity
import com.example.recipefinder.core.domain.model.Ingredient

fun Ingredient.domainToIngredientEntity(recipeId: Int): IngredientEntity {
    return IngredientEntity(
        ingredientId = 0,
        recipeId = recipeId,
        name = this.name,
        amount = this.amount,
        unit = this.unit,
    )
}