package ru.gmpopov.recipeapp.data.model

data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val imageUrl: String,
    val method: List<String>,
)
