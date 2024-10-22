package com.example.recipefinder.features.favouritesRecipes.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipefinder.features.favouritesRecipes.presentation.viewModel.FavouriteRecipesViewModel
import com.example.recipefinder.utils.viewModelFactory.ViewModelFactory
import com.example.recipefinder.utils.viewModelFactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FavouriteRecipesFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteRecipesViewModel::class)
    abstract fun bindViewModel(viewModel: FavouriteRecipesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}