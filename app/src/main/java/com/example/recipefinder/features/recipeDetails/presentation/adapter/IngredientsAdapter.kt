package com.example.recipefinder.features.recipeDetails.presentation.adapter

import com.example.recipefinder.core.domain.model.IModel
import com.example.recipefinder.core.domain.model.Ingredient
import com.example.recipefinder.databinding.ItemIngredientBinding
import com.example.recipefinder.utils.diffCallback.GenericDiffCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class IngredientsAdapter : AsyncListDifferDelegationAdapter<IModel>(GenericDiffCallback()) {

    init {
        delegatesManager.addDelegate(ingredientAdapterDelegate())
    }

    private fun ingredientAdapterDelegate() =
        adapterDelegateViewBinding<Ingredient, IModel, ItemIngredientBinding>(
            { layoutInflater, parent ->
                ItemIngredientBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            }
        ) {

            bind {
                binding.tvIngredientName.text = item.name
                binding.tvIngredientAmount.text = item.amount.toString()
                binding.tvIngredientUnit.text = item.unit
            }
        }
}