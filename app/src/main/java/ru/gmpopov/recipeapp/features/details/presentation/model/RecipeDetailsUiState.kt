package ru.gmpopov.recipeapp.features.details.presentation.model

import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel


class RecipeDetailsUiState(
    val recipe: RecipeUiModel,
    val servings: Int,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val scaledIngredients
        get() = recipe.ingredients.map { ingredient ->
            val multiplier = servings.toFloat() / recipe.servings
            ingredient.copy(
                amount = if (ingredient.amount.toFloatOrNull() == null) {
                    ingredient.amount
                } else {
                    (ingredient.amount.toFloat() * multiplier).toString()
                }
            )
        }

}
