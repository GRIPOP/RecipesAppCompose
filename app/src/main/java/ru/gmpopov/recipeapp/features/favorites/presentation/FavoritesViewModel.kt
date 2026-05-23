package ru.gmpopov.recipeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.gmpopov.recipeapp.data.FavoriteDataStoreManager
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.features.favorites.presentation.model.FavoritesUiState

class FavoritesViewModel(
    application: Application,
    private val repository: RecipesRepositoryStub? = null,
) : AndroidViewModel(application) {

    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)
    val uiState: StateFlow<FavoritesUiState> =
        favoriteDataStoreManager.getFavoriteIdsFlow().map { favoriteIds ->
            if (repository == null) {
                FavoritesUiState(
                    favoriteRecipes = emptyList(),
                    isLoading = false,
                    error = "Репозиторий не инициализирован"
                )
            } else {
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