package com.example.recipefinder.core.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val instructions: String,
    val ingredients: List<Ingredient>
)

