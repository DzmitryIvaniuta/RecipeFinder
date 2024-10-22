package com.example.recipefinder.features.favouritesRecipes.presentation.adapter

import com.example.recipefinder.core.domain.model.IModel
import com.example.recipefinder.core.domain.model.ShortRecipe
import com.example.recipefinder.databinding.ItemRecipeBinding
import com.example.recipefinder.utils.diffCallback.GenericDiffCallback
import com.example.recipefinder.utils.imageLoader.ImageLoader
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class FavouriteRecipesAdapter(
    imageLoader: ImageLoader,
    onItemClicked: (recipeId: Int) -> Unit
): AsyncListDifferDelegationAdapter<IModel>(GenericDiffCallback()) {

    init {
        delegatesManager.addDelegate(shortRecipeAdapterDelegate(imageLoader, onItemClicked))
    }

    private fun shortRecipeAdapterDelegate(
        imageLoader: ImageLoader,
        onItemClicked: (recipeId: Int) -> Unit
    ) =
        adapterDelegateViewBinding<ShortRecipe, IModel, ItemRecipeBinding>(
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