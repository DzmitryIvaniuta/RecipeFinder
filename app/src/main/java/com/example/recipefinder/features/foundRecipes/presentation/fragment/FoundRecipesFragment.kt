package com.example.recipefinder.features.foundRecipes.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipefinder.utils.viewBinding.fragmentViewBinding.viewBinding
import com.example.recipefinder.R
import com.example.recipefinder.app.presentation.App
import com.example.recipefinder.features.foundRecipes.presentation.adapter.FoundRecipesAdapter
import com.example.recipefinder.databinding.FragmentFoundRecipesBinding
import com.example.recipefinder.features.foundRecipes.presentation.viewModel.FoundRecipesViewModel
import com.example.recipefinder.utils.recipeType.RecipeType
import com.example.recipefinder.utils.imageLoader.ImageLoader
import com.example.recipefinder.utils.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoundRecipesFragment : Fragment(R.layout.fragment_found_recipes) {

    private val binding by viewBinding(FragmentFoundRecipesBinding::bind)

    @Inject
    lateinit var imageLoader: ImageLoader

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val foundRecipesViewModel by viewModels<FoundRecipesViewModel> { viewModelFactory }

    private val recipeAdapter by lazy {
        FoundRecipesAdapter(imageLoader) { recipeId ->
            actionAfterClickOnItem(
                recipeId
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as? App)?.appComponent?.inject(this)
    }

    private fun actionAfterClickOnItem(recipeId: Int) {
        foundRecipesViewModel.navigateToDetails(recipeId, RecipeType.FOUND)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
            ?: throw IllegalStateException("View cannot be null")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.apply {
            recipeRecyclerView.layoutManager = LinearLayoutManager(context)
            recipeRecyclerView.adapter = recipeAdapter

            viewLifecycleOwner.lifecycleScope.launch {
                searchFlow()
                    .collectLatest { query ->
                        foundRecipesViewModel.updateSearchQuery(query)
                    }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                foundRecipesViewModel.foundRecipes.flowWithLifecycle(
                    lifecycle,
                    Lifecycle.State.STARTED
                ).collectLatest { pagingData ->
                    recipeAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun searchFlow(): Flow<String> = callbackFlow {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                trySend(s.toString())
            }
        }

        binding.searchEditText.addTextChangedListener(textWatcher)

        awaitClose { binding.searchEditText.removeTextChangedListener(textWatcher) }
    }
}