package com.example.recipefinder.core.presentation.di

import android.content.Context
import com.example.recipefinder.core.data.network.retrofit.SpoonacularApi
import com.example.recipefinder.core.data.repository.RecipeRepositoryImpl
import com.example.recipefinder.core.data.storage.room.RecipeDatabase
import com.example.recipefinder.core.domain.repository.RecipeRepository
import com.example.recipefinder.utils.constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    abstract fun bindRecipeRepository(recipeRepositoryImpl: RecipeRepositoryImpl): RecipeRepository

    companion object {

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }

        @Singleton
        @Provides
        fun provideApiService(okHttpClient: OkHttpClient): SpoonacularApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(SpoonacularApi::class.java)
        }

        @Provides
        fun provideRecipeDatabase(context: Context): RecipeDatabase {
            return RecipeDatabase(context)
        }
    }
}