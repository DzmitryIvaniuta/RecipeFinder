package com.example.recipefinder.utils.mapper

import com.example.recipefinder.core.data.storage.model.RecipeEntity
import com.example.recipefinder.core.domain.model.Recipe

fun Recipe.domainToRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = this.id,
        title = this.title,
        image = this.image,
        readyInMinutes = this.readyInMinutes,
        instructions = this.instructions
    )
}