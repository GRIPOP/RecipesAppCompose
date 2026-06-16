package ru.gmpopov.recipeapp.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.categories.presentation.model.CategoriesUiState
import ru.gmpopov.recipeapp.features.categories.presentation.model.toUiModel

class CategoriesViewModel(private val recipeRepository: RecipesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()


    init {
        loadCategories()
    }

    fun loadCategories() {
        _uiState.update { currentState ->
            currentState.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                recipeRepository.getCategories()
                    .map { categoryDto -> categoryDto.map { it.toUiModel() } }
                    .collect { categories ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                categories = categories,
                                isLoading = false
                            )
                        }
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
