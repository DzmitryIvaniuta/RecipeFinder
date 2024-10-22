package com.example.recipefinder.core.data.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipefinder.core.data.network.retrofit.SpoonacularApi
import com.example.recipefinder.core.domain.model.ShortRecipe
import com.example.recipefinder.utils.constants.API_KEY
import com.example.recipefinder.utils.mapper.responseToShortRecipe

class RecipePagingSource(
    private val api: SpoonacularApi,
    private val query: String
) : PagingSource<Int, ShortRecipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShortRecipe> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.searchRecipes(
                query = query,
                apiKey = API_KEY,
                number = params.loadSize
            )
            val recipes = response.results?.map { it.responseToShortRecipe() } ?: emptyList()

            LoadResult.Page(
                data = recipes,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (recipes.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShortRecipe>): Int? {
        return state.anchorPosition?.let { position ->
            val anchorPage = state.closestPageToPosition(position)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
