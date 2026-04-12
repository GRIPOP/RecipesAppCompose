package ru.gmpopov.recipeapp.navigation

sealed class Destination(val route: String) {
    object Categories : Destination("categories")
    object Favorites : Destination("favorites")
    object Recipes : Destination("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }

    object RecipeItem : Destination("recipe/{recipeId}/{categoryId}") {

        fun createRoute(recipeId: Int, categoryId: Int) = "recipe/$recipeId/$categoryId"
    }
}