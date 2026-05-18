package ru.gmpopov.recipeapp.features.recipes.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipesUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.toUiModel

class RecipesViewModel(
    private val savedStateHandle: SavedStateHandle
) {
    val categoryId: Int = savedStateHandle["categoryId"]
        ?: throw IllegalArgumentException("Категория не найдена")

    val uiState = RecipesUiState(
        categoryTitle = Uri.decode(savedStateHandle["categoryTitle"] ?: ""),
        categoryImageUrl = Uri.decode(savedStateHandle["categoryImageUrl"] ?: ""),
        recipes = RecipesRepositoryStub.getRecipesByCategoryId(categoryId).map { it.toUiModel() },
    )
}