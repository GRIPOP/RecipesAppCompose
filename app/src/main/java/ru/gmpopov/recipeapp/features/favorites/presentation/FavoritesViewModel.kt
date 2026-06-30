package ru.gmpopov.recipeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.gmpopov.recipeapp.data.FavoriteDataStoreManager
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.features.favorites.presentation.model.FavoritesUiState

@HiltViewModel
class FavoritesViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)
    private val repository = RecipesRepositoryStub

    val uiState: StateFlow<FavoritesUiState> =
        favoriteDataStoreManager.getFavoriteIdsFlow().map { favoriteIds ->
            try {
                val recipes = favoriteIds.mapNotNull { id ->
                    repository.getRecipeById(id.toInt())
                }
                FavoritesUiState(
                    favoriteRecipes = recipes,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                FavoritesUiState(
                    favoriteRecipes = emptyList(),
                    isLoading = false,
                    error = "Ошибка загрузки данных",
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            FavoritesUiState(
                favoriteRecipes = emptyList(),
                isLoading = true,
                error = null,
            ),
        )
}