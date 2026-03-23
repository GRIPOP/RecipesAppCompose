package ru.gmpopov.recipeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import ru.gmpopov.recipeapp.core.ui.navigation.BottomNavigation
import ru.gmpopov.recipeapp.ui.categories.CategoriesScreen
import ru.gmpopov.recipeapp.ui.favorites.FavoritesScreen
import ru.gmpopov.recipeapp.ui.recipes.RecipesScreen
import ru.gmpopov.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun RecipesApp() {
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    var selectedCategoryTitle by remember { mutableStateOf("") }
    var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }

    RecipeAppTheme {
        Scaffold(
            content = { paddingValues ->
                when (currentScreen) {
                    ScreenId.CATEGORIES -> {
                        CategoriesScreen(
                            modifier = Modifier.padding(paddingValues),
                            onCategoryClick = { categoryId, categoryTitle ->
                                selectedCategoryId = categoryId
                                selectedCategoryTitle = categoryTitle
                                currentScreen = ScreenId.RECIPES
                            },
                        )
                    }

                    ScreenId.FAVORITES -> {
                        FavoritesScreen(
                            modifier = Modifier.padding(paddingValues)

                        )
                    }

                    ScreenId.RECIPES -> {
                        RecipesScreen(
                            categoryId = selectedCategoryId ?: error("Category ID is required"),
                            categoryTitle = selectedCategoryTitle,
                            onRecipeClick = {},
                            modifier = Modifier.padding(paddingValues),
                        )
                    }
                }
            },
            bottomBar = {
                BottomNavigation(
                    { currentScreen = ScreenId.CATEGORIES },
                    { currentScreen = ScreenId.FAVORITES })
            }
        )
    }
}