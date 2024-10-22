package com.example.recipefinder.core.data.network.models.detailsResponseModel

import com.google.gson.annotations.SerializedName

data class RecipeDetailResponseModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("readyInMinutes") val readyInMinutes: Int?,
    @SerializedName("servings") val servings: Int?,
    @SerializedName("summary") val summary: String?,
    @SerializedName("instructions") val instructions: String?,
    @SerializedName("extendedIngredients") val ingredients: List<IngredientResponseModel>?,
)