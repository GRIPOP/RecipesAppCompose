package ru.gmpopov.recipeapp.fixtures

import ru.gmpopov.recipeapp.data.model.IngredientDto
import ru.gmpopov.recipeapp.data.model.RecipeDto

object RecipeTestFixtures {
    fun createIngredientDto(
        quantity: String = "200",
        unitOfMeasure: String = "г",
        description: String = "котлета",
    ) = IngredientDto(quantity = quantity, unitOfMeasure = unitOfMeasure, description = description)

    fun createRecipeDto(
        id: Int = 1,
        title: String = "Бургер",
        ingredients: List<IngredientDto> = listOf(createIngredientDto()),
        method: List<String> = listOf("Обжарить с каждой стороны по 3 минуты"),
        imageUrl: String = "burger.png",
    ) = RecipeDto(
        id = id,
        title = title,
        ingredients = ingredients,
        method = method,
        imageUrl = imageUrl,
    )

    fun createRecipeDtoList(count: Int = 3) =
        List(count) { index ->
            createRecipeDto(
                id = index + 1,
                title = "Recipe ${index + 1}",
            )
        }
}