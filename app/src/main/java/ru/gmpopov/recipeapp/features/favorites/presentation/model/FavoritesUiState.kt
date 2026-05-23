package ru.gmpopov.recipeapp.features.favorites.presentation.model

import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel

data class FavoritesUiState(
    val favoriteRecipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)