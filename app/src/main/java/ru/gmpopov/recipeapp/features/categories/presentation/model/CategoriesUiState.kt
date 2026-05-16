package ru.gmpopov.recipeapp.features.categories.presentation.model

data class CategoriesUiState(
    val categories: List<CategoryUiModel> = emptyList(),
    var isLoading: Boolean = false,
    val error: String? = null,
)
