package ru.gmpopov.recipeapp.features.recipes.presentation.model

data class RecipesUiState(
    val categoryTitle: String,
    val categoryImageUrl: String,
    val recipes: List<RecipeUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEmptyRecipes: Boolean = false,
)
