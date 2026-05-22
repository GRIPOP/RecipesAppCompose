package ru.gmpopov.recipeapp.features.recipes.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipesUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.toUiModel

class RecipesViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val categoryId: Int = savedStateHandle["categoryId"]
        ?: throw IllegalArgumentException("Категория не найдена")

    private val _uiState = MutableStateFlow(
        RecipesUiState(
            categoryTitle = Uri.decode(savedStateHandle["categoryTitle"] ?: ""),
            categoryImageUrl = Uri.decode(savedStateHandle["categoryImageUrl"] ?: ""),
            recipes = RecipesRepositoryStub.getRecipesByCategoryId(categoryId)
                .map { it.toUiModel() },
        )
    )

    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()
}