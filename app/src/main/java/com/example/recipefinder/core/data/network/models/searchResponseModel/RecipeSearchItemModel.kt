package com.example.recipefinder.core.data.network.models.searchResponseModel

import com.google.gson.annotations.SerializedName

data class RecipeSearchItemModel(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("imageType") val imageType: String?
)
