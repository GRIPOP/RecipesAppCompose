package ru.gmpopov.recipeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.data.FavoriteDataStoreManager
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.details.presentation.model.RecipeDetailsUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.toUiModel

class RecipeDetailsViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : AndroidViewModel(application) {
    private val recipeId = savedStateHandle.get<Int>("recipeId")
        ?: throw IllegalArgumentException("recipeId is required")
    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)
    private val _uiState =
        MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRecipe(recipeId)

        viewModelScope.launch {
            favoriteDataStoreManager.getFavoriteIdsFlow().collect { favoriteIds ->
                _uiState.update { currentRecipeDetailsUiState ->
                    currentRecipeDetailsUiState.copy(
                        isFavorite = currentRecipeDetailsUiState.recipe?.id?.let { id ->
                            favoriteIds.contains(id.toString())
                        } ?: false
                    )
                }
            }
        }
    }

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update { currentRecipeDetailsUiState ->
                currentRecipeDetailsUiState.copy(isLoading = true)
            }

            try {
                val loadedRecipe = repository
                    .getRecipe(recipeId)

                _uiState.update { currentRecipeDetailsUiState ->
                    currentRecipeDetailsUiState.copy(
                        recipe = loadedRecipe.toUiModel(),
                        servings = loadedRecipe.servings,
                        isLoading = false,
                        error = null,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentRecipeDetailsUiState ->
                    currentRecipeDetailsUiState.copy(
                        error = "Ошибка загрузки: ${e.message}",
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _uiState.value.recipe?.id?.let { recipeId ->
                if (_uiState.value.isFavorite) {
                    favoriteDataStoreManager.removeFavorite(recipeId)
                } else {
                    favoriteDataStoreManager.addFavorite(recipeId)
                }
            }
        }
    }

    fun updatePortions(newServings: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                servings = newServings
            )
        }
    }
}
