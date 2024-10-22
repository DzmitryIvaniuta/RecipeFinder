package com.example.recipefinder.core.data.network.retrofit

import com.example.recipefinder.core.data.network.models.detailsResponseModel.RecipeDetailResponseModel
import com.example.recipefinder.core.data.network.models.searchResponseModel.RecipeSearchResponseModel
import com.example.recipefinder.utils.constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApi {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("number") number: Int = 10
    ): RecipeSearchResponseModel

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetails(
        @Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): RecipeDetailResponseModel
}