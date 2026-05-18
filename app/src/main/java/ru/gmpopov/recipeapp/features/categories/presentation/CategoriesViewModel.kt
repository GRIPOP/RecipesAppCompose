package ru.gmpopov.recipeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.features.categories.presentation.model.CategoriesUiState
import ru.gmpopov.recipeapp.features.categories.presentation.model.toUiModel

class CategoriesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                val categories =
                    RecipesRepositoryStub.getCategories()
                        .map { categoryDto -> categoryDto.toUiModel() }

                _uiState.update { currentState ->
                    currentState.copy(
                        categories = categories,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        error = "Ошибка загрузки данных"
                    )
                }
            }
        }
    }
}