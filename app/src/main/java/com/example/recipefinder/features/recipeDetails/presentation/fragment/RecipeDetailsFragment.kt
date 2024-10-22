package com.example.recipefinder.features.recipeDetails.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefinder.R
import com.example.recipefinder.app.presentation.App
import com.example.recipefinder.core.domain.model.Recipe
import com.example.recipefinder.features.recipeDetails.presentation.adapter.IngredientsAdapter
import com.example.recipefinder.databinding.FragmentRecipeDetailsBinding
import com.example.recipefinder.features.favouritesRecipes.presentation.fragment.FavouriteRecipesFragment
import com.example.recipefinder.features.recipeDetails.presentation.dialog.DeleteConfirmationDialogFragment
import com.example.recipefinder.features.recipeDetails.presentation.viewModel.RecipeDetailsViewModel
import com.example.recipefinder.utils.constants.RECIPE_ID_KEY
import com.example.recipefinder.utils.constants.RECIPE_TYPE_KEY
import com.example.recipefinder.utils.recipeState.RecipeState
import com.example.recipefinder.utils.recipeType.RecipeType
import com.example.recipefinder.utils.bundleExtensions.getSerializableCompat
import com.example.recipefinder.utils.imageLoader.ImageLoader
import com.example.recipefinder.utils.viewBinding.fragmentViewBinding.viewBinding
import com.example.recipefinder.utils.viewModelFactory.ViewModelFactory
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    private val binding by viewBinding(FragmentRecipeDetailsBinding::bind)

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val recipeDetailsViewModel by viewModels<RecipeDetailsViewModel> { viewModelFactory }

    private val ingredientsAdapter = IngredientsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as? App)?.appComponent?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
            ?: throw IllegalStateException("View cannot be null")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId: Int? = arguments?.getInt(RECIPE_ID_KEY)
        val recipeType: RecipeType? = arguments.getSerializableCompat(RECIPE_TYPE_KEY)

        if (recipeId != null) {
            if (recipeType == RecipeType.FOUND) {
                recipeDetailsViewModel.getFoundRecipeDetails(recipeId)
            } else {
                recipeDetailsViewModel.getFavouriteRecipeDetails(recipeId)
            }
        }

        recipeType?.let { recipeDetailsViewModel.setRecipeFlow(it) }

        binding.apply {

            ingredientsList.layoutManager = LinearLayoutManager(context)
            ingredientsList.adapter = ingredientsAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                recipeDetailsViewModel.recipeFlow.collect { state ->
                    when (state) {
                        is RecipeState.Loading -> {
                            progressBar.isVisible = true
                        }

                        is RecipeState.Success -> {
                            progressBar.isVisible = false
                            val recipe = state.recipe
                            ingredientsAdapter.items = recipe.ingredients
                            loadImageWithGlide(recipe)
                            recipeDetailTitleTextView.text = recipe.title
                            instructionsLabelTextView.text = recipe.instructions
                            recipeDetailTimeTextView.text =
                                getString(R.string.time, recipe.readyInMinutes.toString())

                            if (recipeType == RecipeType.FOUND) {

                                saveBtn.setOnClickListener {
                                    recipeDetailsViewModel.saveRecipe(recipe)
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.recipe_saved), Toast.LENGTH_SHORT
                                    ).show()
                                }
                                deleteBtn.isVisible = false
                            } else {

                                saveBtn.isVisible = false
                                deleteBtn.setOnClickListener {
                                    val dialog = DeleteConfirmationDialogFragment {
                                        recipeDetailsViewModel.deleteRecipe(recipe.id)
                                        router.navigateTo(FragmentScreen { FavouriteRecipesFragment() })
                                        Toast.makeText(
                                            requireContext(),
                                            R.string.recipe_deleted,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    dialog.show(parentFragmentManager, DeleteConfirmationDialogFragment.TAG)
                                }
                            }
                        }

                        is RecipeState.Error -> {
                            throw IllegalStateException("Recipe should exist")
                        }
                    }
                }
            }
        }
    }

    private fun loadImageWithGlide(recipe: Recipe?) {
        imageLoader.loadImage(recipe?.image, binding.recipeDetailImageView)
    }

    companion object {
        fun newInstance(recipeId: Int, recipeType: RecipeType): RecipeDetailsFragment {
            return RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECIPE_ID_KEY, recipeId)
                    putSerializable(RECIPE_TYPE_KEY, recipeType)
                }
            }
        }
    }
}