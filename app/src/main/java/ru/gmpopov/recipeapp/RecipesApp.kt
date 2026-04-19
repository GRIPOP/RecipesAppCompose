package ru.gmpopov.recipeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.gmpopov.recipeapp.core.ui.navigation.BottomNavigation
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.navigation.Destination
import ru.gmpopov.recipeapp.ui.categories.CategoriesScreen
import ru.gmpopov.recipeapp.ui.details.RecipeDetailsScreen
import ru.gmpopov.recipeapp.ui.favorites.FavoritesScreen
import ru.gmpopov.recipeapp.ui.recipes.RecipesScreen
import ru.gmpopov.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun RecipesApp(deepLinkIntent: Intent? = null) {
    RecipeAppTheme {
        val navController = rememberNavController()

        LaunchedEffect(deepLinkIntent) {
            deepLinkIntent?.data?.let { uri ->
                val recipeId: Int? = when (uri.scheme) {
                    "recipeapp" ->
                        if (uri.host == "recipe") uri.pathSegments[0].toIntOrNull() else null

                    "https", "http" ->
                        if (uri.pathSegments[0] == "recipe") uri.pathSegments[1].toIntOrNull() else null

                    else -> null
                }

                if (recipeId != null) {
                    navController.navigate(Destination.RecipeItem.createRoute(recipeId))
                }
            }
        }

        Scaffold(
            content = { paddingValues ->
                NavHost(
                    navController = navController,
                    startDestination = Destination.Categories.route,
                ) {
                    composable(route = Destination.Categories.route) {
                        CategoriesScreen(
                            modifier = Modifier.padding(paddingValues),
                            onCategoryClick = { categoryId, _ ->
                                navController.navigate(
                                    Destination.Recipes.createRoute(
                                        categoryId
                                    )
                                )
                            },
                        )
                    }

                    composable(route = Destination.Favorites.route) {
                        FavoritesScreen(
                            modifier = Modifier.padding(paddingValues),
                        )
                    }

                    composable(
                        route = Destination.Recipes.route,
                        arguments = listOf(navArgument("categoryId") {
                            type = NavType.IntType
                        }),
                    ) { backStackEntry ->
                        val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
                        RecipesScreen(
                            categoryId = categoryId,
                            categoryTitle = remember(categoryId) {
                                RecipesRepositoryStub.getCategories()
                                    .find { it.id == categoryId }?.title ?: ""
                            },
                            onRecipeClick = { recipeId, recipe ->
                                navController.navigate(
                                    Destination.RecipeItem.createRoute(recipeId)
                                )
                            },
                            modifier = Modifier.padding(paddingValues),
                        )
                    }

                    composable(
                        route = Destination.RecipeItem.route,
                        arguments = listOf(
                            navArgument("recipeId") { type = NavType.IntType },
                        )
                    ) { backStackEntry ->
                        val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
                        val recipe = RecipesRepositoryStub.getRecipeById(recipeId)
                        recipe?.let { recipe ->
                            RecipeDetailsScreen(
                                recipe,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                    }
                }
            },
            bottomBar = {
                BottomNavigation(
                    onCategoriesClick = {
                        navController.navigate(Destination.Categories.route) {
                            launchSingleTop = true
                        }
                    },
                    onFavoriteClick = {
                        navController.navigate(Destination.Favorites.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        )
    }
}
