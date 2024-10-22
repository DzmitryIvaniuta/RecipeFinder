package com.example.recipefinder.features.foundRecipes.presentation.adapter

import com.example.recipefinder.core.domain.model.ShortRecipe
import com.example.recipefinder.databinding.ItemRecipeBinding
import com.example.recipefinder.utils.adapter.PagingDataDelegationAdapter
import com.example.recipefinder.utils.diffCallback.GenericDiffCallback
import com.example.recipefinder.utils.imageLoader.ImageLoader
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class FoundRecipesAdapter(
    imageLoader: ImageLoader,
    onItemClicked: (recipeId: Int) -> Unit
) : PagingDataDelegationAdapter<ShortRecipe>(GenericDiffCallback()) {

    init {
        delegatesManager.addDelegate(shortRecipeAdapterDelegate(imageLoader, onItemClicked))
    }

    private fun shortRecipeAdapterDelegate(
        imageLoader: ImageLoader,
        onItemClicked: (recipeId: Int) -> Unit
    ) = adapterDelegateViewBinding<ShortRecipe, ShortRecipe, ItemRecipeBinding>(
        { layoutInflater, parent -> ItemRecipeBinding.inflate(layoutInflater, parent, false) }
    ) {
        binding.root.setOnClickListener {
            onItemClicked(item.id)
        }

        bind {
            binding.recipeTitleTextView.text = item.title
            imageLoader.loadImage(item.image, binding.recipeImageView)
        }
    }
}
