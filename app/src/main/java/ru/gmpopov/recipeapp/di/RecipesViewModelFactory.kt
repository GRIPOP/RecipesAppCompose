package ru.gmpopov.recipeapp.di

import androidx.lifecycle.SavedStateHandle
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.recipes.presentation.RecipesViewModel

class RecipesViewModelFactory(
    val savedStateHandle: SavedStateHandle,
    val repository: RecipesRepository
): Factory<RecipesViewModel> {
    override fun create(): RecipesViewModel = RecipesViewModel(savedStateHandle, repository)
}