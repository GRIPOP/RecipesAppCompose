package ru.gmpopov.recipeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.data.FavoriteDataStoreManager
import ru.gmpopov.recipeapp.features.details.presentation.model.RecipeDetailsUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel

class RecipeDetailsViewModel(
    application: Application,
    private val recipe: RecipeUiModel,
) : AndroidViewModel(application) {
    val favoriteDataStoreManager = FavoriteDataStoreManager(application)
    private val _uiState =
        MutableStateFlow(RecipeDetailsUiState(recipe = recipe, servings = recipe.servings))
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteDataStoreManager.getFavoriteIdsFlow().collect { favoriteIds ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isFavorite = favoriteIds.contains(recipe.id.toString())
                    )
                }
            }
        }
    }

    suspend fun toggleFavorite() {
        if (_uiState.value.isFavorite) {
            favoriteDataStoreManager.removeFavorite(recipe.id)
        } else {
            favoriteDataStoreManager.addFavorite(recipe.id)
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
