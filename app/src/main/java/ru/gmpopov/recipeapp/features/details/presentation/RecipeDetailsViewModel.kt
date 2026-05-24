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
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.features.details.presentation.model.RecipeDetailsUiState
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel

class RecipeDetailsViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)
    private val _uiState =
        MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            favoriteDataStoreManager.getFavoriteIdsFlow().collect { favoriteIds ->
                _uiState.update { currentState ->
                    currentState.copy(
                        isFavorite = currentState.recipe?.id?.let { id ->
                            favoriteIds.contains(id.toString())
                        } ?: false,
                    )
                }
            }
        }
    }

    fun initializeWithRecipe(recipe: RecipeUiModel) {
        _uiState.update { currentRecipeDetailsUiState ->
            currentRecipeDetailsUiState.copy(
                recipe = recipe,
                servings = recipe.servings,
            )
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
