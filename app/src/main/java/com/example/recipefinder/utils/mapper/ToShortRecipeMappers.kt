package com.example.recipefinder.utils.mapper

import com.example.recipefinder.core.data.network.models.searchResponseModel.RecipeSearchItemModel
import com.example.recipefinder.core.data.storage.model.RecipeEntity
import com.example.recipefinder.core.domain.model.ShortRecipe

fun RecipeSearchItemModel.responseToShortRecipe(): ShortRecipe {
    return ShortRecipe(
        id = this.id ?: throw IllegalArgumentException("Recipe ID is missing"),
        title = this.title ?: "Unknown Recipe",
        image = this.image ?: ""
    )
}

fun RecipeEntity.entityToShortRecipe(): ShortRecipe {
    return ShortRecipe(
        id = this.id,
        title = this.title,
        image = this.image
    )
}

