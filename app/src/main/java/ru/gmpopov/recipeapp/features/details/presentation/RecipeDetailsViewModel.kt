package ru.gmpopov.recipeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.data.FavoriteDataStoreManager
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.details.presentation.model.RecipeDetailsUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    val favoriteDataStoreManager: FavoriteDataStoreManager,
    savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : ViewModel() {
    private val recipeId = savedStateHandle.get<Int>("recipeId")
        ?: throw IllegalArgumentException("recipeId is required")
    private val _uiState =
        MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getRecipe(recipeId).collect { recipeDto ->
                if (recipeDto != null) {
                    val recipeUi = recipeDto.toUiModel()
                    _uiState.update { currentState ->
                        currentState.copy(
                            recipe = recipeUi,
                            isLoading = false,
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(isLoading = true)
                    }
                }
            }
        }

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
