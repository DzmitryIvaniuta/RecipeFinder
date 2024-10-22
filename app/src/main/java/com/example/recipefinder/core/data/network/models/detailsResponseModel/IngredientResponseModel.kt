package com.example.recipefinder.core.data.network.models.detailsResponseModel

import com.google.gson.annotations.SerializedName

data class IngredientResponseModel (
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("amount") val amount: Double?,
    @SerializedName("unit") val unit: String?,
    @SerializedName("original") val original: String?
)