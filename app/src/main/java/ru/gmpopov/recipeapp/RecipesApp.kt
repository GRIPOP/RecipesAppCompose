package ru.gmpopov.recipeapp

import android.app.Application
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
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.gmpopov.recipeapp.core.ui.navigation.BottomNavigation
import ru.gmpopov.recipeapp.core.navigation.Destination
import ru.gmpopov.recipeapp.core.network.NetworkConfig
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.features.categories.ui.CategoriesScreen
import ru.gmpopov.recipeapp.features.details.ui.RecipeDetailsScreen
import ru.gmpopov.recipeapp.features.favorites.ui.FavoritesScreen
import ru.gmpopov.recipeapp.features.recipes.ui.RecipesScreen
import ru.gmpopov.recipeapp.core.ui.theme.RecipeAppTheme
import ru.gmpopov.recipeapp.core.utils.DEEP_LINK_SCHEME
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryImpl
import ru.gmpopov.recipeapp.features.categories.presentation.CategoriesViewModel
import ru.gmpopov.recipeapp.features.details.presentation.RecipeDetailsViewModel
import ru.gmpopov.recipeapp.features.recipes.presentation.RecipesViewModel
import java.util.concurrent.TimeUnit

@Composable
fun RecipesApp(deepLinkIntent: Intent? = null) {

    val contentType: MediaType = remember { "application/json".toMediaType() }
    val json = remember {
        Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
    }
    
    val context = LocalContext.current

    val database = remember { RecipesDatabase.buildDatabase(context) }


    val okHttpClient = remember {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    val retrofit: Retrofit = remember {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    val apiService = remember { retrofit.create(RecipesApiService::class.java) }

    val recipesRepositoryImpl: RecipesRepositoryImpl =
        remember { RecipesRepositoryImpl(apiService, database) }

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
                        val categoryViewModel = remember {
                            CategoriesViewModel(recipesRepositoryImpl)
                        }
                        CategoriesScreen(
                            viewModel = categoryViewModel,
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
                        val recipesViewModel = remember(backStackEntry) {
                            RecipesViewModel(
                                savedStateHandle = backStackEntry.savedStateHandle,
                                repository = recipesRepositoryImpl,
                            )
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
                        val context = LocalContext.current
                        val recipeDetailsViewModel = remember(backStackEntry) {
                            RecipeDetailsViewModel(
                                context.applicationContext as Application,
                                backStackEntry.savedStateHandle,
                                recipesRepositoryImpl
                            )
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
