package com.example.recipefinder.core.domain.model

data class Ingredient(
    val id: Int,
    val name: String,
    val amount: Double,
    val unit: String,
): IModel
