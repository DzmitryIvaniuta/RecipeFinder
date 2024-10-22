package com.example.recipefinder.core.presentation.di

import com.example.recipefinder.utils.imageLoader.GlideImageLoader
import com.example.recipefinder.utils.imageLoader.ImageLoader
import dagger.Binds
import dagger.Module

@Module
abstract class ImageLoaderModule {

    @Binds
    abstract fun bindImageLoader(glideImageLoader: GlideImageLoader): ImageLoader
}

