package ru.gmpopov.recipeapp.features.recipes.presentation

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipesUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.toUiModel

@HiltViewModel
class RecipesViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : ViewModel() {

    val categoryId: Int = savedStateHandle["categoryId"]
        ?: throw IllegalArgumentException("Категория не найдена")

    private val _uiState = MutableStateFlow(
        RecipesUiState(
            categoryTitle = Uri.decode(savedStateHandle["categoryTitle"] ?: ""),
            categoryImageUrl = Uri.decode(savedStateHandle["categoryImageUrl"] ?: ""),
            recipes = emptyList(),
        )
    )

    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                repository.getRecipesByCategory(categoryId)
                    .map { recipeDto -> recipeDto.map { it.toUiModel() } }
                    .collect { recipes ->

                        _uiState.update { currentState ->
                            currentState.copy(
                                recipes = recipes,
                                isLoading = false,
                            )
                        }
                    }

            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        recipes = emptyList(),
                        isLoading = false,
                        error = "Ошибка загрузки данных",
                    )
                }
            }
        }
    }
}