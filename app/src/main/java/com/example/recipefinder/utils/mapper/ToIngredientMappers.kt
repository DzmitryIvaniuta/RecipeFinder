package com.example.recipefinder.utils.mapper

import com.example.recipefinder.core.data.network.models.detailsResponseModel.IngredientResponseModel
import com.example.recipefinder.core.data.storage.model.IngredientEntity
import com.example.recipefinder.core.domain.model.Ingredient


fun IngredientEntity.entityToIngredient(): Ingredient {
    return Ingredient(
        id = this.ingredientId,
        name = this.name,
        amount = this.amount,
        unit = this.unit,
    )
}

fun IngredientResponseModel.responseToIngredient(): Ingredient {
    return Ingredient(
        id = this.id ?: throw IllegalArgumentException("Ingredient ID is missing"),
        name = this.name ?: "Unknown ingredient",
        amount = this.amount ?: 0.0,
        unit = this.unit ?: "units",
    )
}