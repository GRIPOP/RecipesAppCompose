package ru.gmpopov.recipeapp.features.recipes.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipesUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.toUiModel

class RecipesViewModel(
    private val savedStateHandle: SavedStateHandle
) {
    val categoryId: Int = savedStateHandle.get("categoryId")
        ?: throw IllegalArgumentException("Категория не найдена")

    val uiState = RecipesUiState(
        categoryTitle = Uri.decode(savedStateHandle.get("categoryTitle") ?: ""),
        categoryImageUrl = Uri.decode(savedStateHandle.get("categoryImageUrl") ?: ""),
        recipes = RecipesRepositoryStub.getRecipesByCategoryId(categoryId).map { it.toUiModel() },
    )
}