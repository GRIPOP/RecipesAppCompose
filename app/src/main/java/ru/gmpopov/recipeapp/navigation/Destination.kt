package ru.gmpopov.recipeapp.navigation

import ru.gmpopov.recipeapp.DEEP_LINK_BASE_URL

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }

    object RecipeItem : Destination("recipe/{recipeId}/{categoryId}") {

        fun createRoute(recipeId: Int, categoryId: Int) = "recipe/$recipeId/$categoryId"

        fun createRecipeDeepLink(recipeId: Int, categoryId: Int) =
            "$DEEP_LINK_BASE_URL/recipe/$recipeId/$categoryId"
    }

}