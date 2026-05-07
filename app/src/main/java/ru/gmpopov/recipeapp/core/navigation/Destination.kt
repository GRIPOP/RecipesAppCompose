package ru.gmpopov.recipeapp.core.navigation

import ru.gmpopov.recipeapp.core.utils.DEEP_LINK_BASE_URL

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }

    object RecipeItem : Destination("recipe/{recipeId}") {

        fun createRoute(recipeId: Int) = "recipe/$recipeId"

        fun createRecipeDeepLink(recipeId: Int) =
            "$DEEP_LINK_BASE_URL/recipe/$recipeId"
    }
}