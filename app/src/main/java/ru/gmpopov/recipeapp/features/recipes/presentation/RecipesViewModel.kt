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
    val categoryTitle: String = Uri.decode(savedStateHandle.get("categoryTitle") ?: "")
    val categoryImageUrl: String = Uri.decode(savedStateHandle.get("categoryImageUrl") ?: "")

    val recipes = RecipesRepositoryStub.getRecipesByCategoryId(categoryId).map {
        it.toUiModel()
    }

    val uiState = RecipesUiState(
        categoryTitle = categoryTitle,
        categoryImageUrl = categoryImageUrl,
        recipes = recipes,
    )
}