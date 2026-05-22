package ru.gmpopov.recipeapp.features.details.presentation.model

import ru.gmpopov.recipeapp.features.recipes.presentation.model.IngredientUiModel
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel


data class RecipeDetailsUiState(
    val recipe: RecipeUiModel? = null,
    val servings: Int = 1,
    val isFavorite: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val scaledIngredients: List<IngredientUiModel>?
        get() = recipe?.ingredients?.map { ingredient ->
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
