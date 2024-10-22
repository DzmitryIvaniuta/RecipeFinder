package com.example.recipefinder.features.foundRecipes.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipefinder.features.foundRecipes.presentation.viewModel.FoundRecipesViewModel
import com.example.recipefinder.utils.viewModelFactory.ViewModelFactory
import com.example.recipefinder.utils.viewModelFactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FoundRecipesFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(FoundRecipesViewModel::class)
    abstract fun bindViewModel(viewModel: FoundRecipesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}