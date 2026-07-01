package ru.gmpopov.recipeapp

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.gmpopov.recipeapp.core.ui.navigation.BottomNavigation
import ru.gmpopov.recipeapp.core.navigation.Destination
import ru.gmpopov.recipeapp.features.categories.ui.CategoriesScreen
import ru.gmpopov.recipeapp.features.details.ui.RecipeDetailsScreen
import ru.gmpopov.recipeapp.features.favorites.ui.FavoritesScreen
import ru.gmpopov.recipeapp.features.recipes.ui.RecipesScreen
import ru.gmpopov.recipeapp.core.ui.theme.RecipeAppTheme
import ru.gmpopov.recipeapp.core.utils.DEEP_LINK_SCHEME
import ru.gmpopov.recipeapp.di.RecipeApplication
import ru.gmpopov.recipeapp.di.RecipeDetailsViewModelFactory
import ru.gmpopov.recipeapp.di.RecipesViewModelFactory

@Composable
fun RecipesApp(deepLinkIntent: Intent? = null) {

    RecipeAppTheme {
        val navController = rememberNavController()

        LaunchedEffect(deepLinkIntent) {
            deepLinkIntent?.data?.let { uri ->
                val recipeId: Int? = when (uri.scheme) {
                    DEEP_LINK_SCHEME ->
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
                            onCategoryClick = { categoryId, categoryTitle, categoryImageUrl ->
                                navController.navigate(
                                    Destination.Recipes.createRoute(
                                        categoryId,
                                        categoryTitle,
                                        categoryImageUrl,
                                    )
                                )
                            },
                        )
                    }

                    composable(route = Destination.Favorites.route) {
                        FavoritesScreen(
                            onClickRecipeCard = { recipeId ->
                                navController.navigate(Destination.RecipeItem.createRoute(recipeId))
                            },
                            modifier = Modifier.padding(paddingValues),
                        )
                    }

                    composable(
                        route = Destination.Recipes.route,
                        arguments = listOf(
                            navArgument("categoryId") { type = NavType.IntType },
                            navArgument("categoryTitle") { type = NavType.StringType },
                            navArgument("categoryImageUrl") { type = NavType.StringType },
                        ),
                    ) { backStackEntry ->
                        val appContainer =
                            (LocalContext.current.applicationContext as? RecipeApplication)?.appContainer
                        val recipesViewModel = remember(backStackEntry) {
                            appContainer?.let {
                                RecipesViewModelFactory(
                                    backStackEntry.savedStateHandle,
                                    appContainer.recipesRepository,
                                ).create()
                            } ?: error("AppContainer is null")
                        }

                        RecipesScreen(
                            onRecipeClick = { recipeId, _ ->
                                navController.navigate(
                                    Destination.RecipeItem.createRoute(recipeId)
                                )
                            },
                            modifier = Modifier.padding(paddingValues),
                            viewModel = recipesViewModel,
                        )
                    }

                    composable(
                        route = Destination.RecipeItem.route,
                        arguments = listOf(
                            navArgument("recipeId") { type = NavType.IntType },
                        )
                    ) { backStackEntry ->
                        val application =
                            LocalContext.current.applicationContext as? RecipeApplication
                        val appContainer = application?.appContainer
                        val recipeDetailsViewModel = remember(backStackEntry) {
                            appContainer?.let {
                                RecipeDetailsViewModelFactory(
                                    application,
                                    backStackEntry.savedStateHandle,
                                    appContainer.recipesRepository,
                                ).create()
                            } ?: error("AppContainer is null")
                        }
                        RecipeDetailsScreen(
                            modifier = Modifier.padding(paddingValues),
                            viewModel = recipeDetailsViewModel,
                        )
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
