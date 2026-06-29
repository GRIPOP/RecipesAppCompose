package ru.gmpopov.recipeapp.di

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.details.presentation.RecipeDetailsViewModel

class RecipeDetailsViewModelFactory(
    val application: Application,
    val savedStateHandle: SavedStateHandle,
    val repository: RecipesRepository,
): Factory<RecipeDetailsViewModel> {
    override fun create(): RecipeDetailsViewModel =
        RecipeDetailsViewModel(
            application,
            savedStateHandle,
            repository
        )
}