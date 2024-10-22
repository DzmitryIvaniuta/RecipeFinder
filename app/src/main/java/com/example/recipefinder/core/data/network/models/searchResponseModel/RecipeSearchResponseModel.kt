package com.example.recipefinder.core.data.network.models.searchResponseModel

import com.google.gson.annotations.SerializedName

data class RecipeSearchResponseModel(
    @SerializedName("results") val results: List<RecipeSearchItemModel>?,
    @SerializedName("totalResults") val totalResults: Int?,
    @SerializedName("offset") val offset: Int?,
    @SerializedName("number") val number: Int?
)
