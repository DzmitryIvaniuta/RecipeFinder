package com.example.recipefinder.features.recipeDetails.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipefinder.features.recipeDetails.presentation.viewModel.RecipeDetailsViewModel
import com.example.recipefinder.utils.viewModelFactory.ViewModelFactory
import com.example.recipefinder.utils.viewModelFactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RecipeDetailsFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecipeDetailsViewModel::class)
    abstract fun bindViewModel(viewModel: RecipeDetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}