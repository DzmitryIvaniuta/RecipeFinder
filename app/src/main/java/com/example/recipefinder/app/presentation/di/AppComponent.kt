package com.example.recipefinder.app.presentation.di

import com.example.recipefinder.core.presentation.activity.MainActivity
import com.example.recipefinder.core.presentation.di.DataModule
import com.example.recipefinder.core.presentation.di.ImageLoaderModule
import com.example.recipefinder.features.favouritesRecipes.presentation.di.FavouriteRecipesFragmentModule
import com.example.recipefinder.features.favouritesRecipes.presentation.fragment.FavouriteRecipesFragment
import com.example.recipefinder.features.foundRecipes.presentation.di.FoundRecipesFragmentModule
import com.example.recipefinder.features.foundRecipes.presentation.fragment.FoundRecipesFragment
import com.example.recipefinder.features.recipeDetails.presentation.di.RecipeDetailsFragmentModule
import com.example.recipefinder.features.recipeDetails.presentation.fragment.RecipeDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    DataModule::class,
    FoundRecipesFragmentModule::class,
    FavouriteRecipesFragmentModule::class,
    RecipeDetailsFragmentModule::class,
    NavigationModule::class,
    ImageLoaderModule::class]
)
interface AppComponent {

    fun inject(foundRecipesFragment: FoundRecipesFragment)

    fun inject(favouriteRecipesFragment: FavouriteRecipesFragment)

    fun inject(recipeDetailsFragment: RecipeDetailsFragment)

    fun inject(mainActivity: MainActivity)
}