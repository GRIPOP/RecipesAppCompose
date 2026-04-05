package ru.gmpopov.recipeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.gmpopov.recipeapp.core.ui.navigation.BottomNavigation
import ru.gmpopov.recipeapp.ui.categories.CategoriesScreen
import ru.gmpopov.recipeapp.ui.favorites.FavoritesScreen
import ru.gmpopov.recipeapp.ui.recipes.RecipesScreen
import ru.gmpopov.recipeapp.ui.theme.RecipeAppTheme

@Preview
@Composable
fun RecipesApp() {
    RecipeAppTheme {
        var currentScreen by remember { mutableStateOf(ScreenId.RECIPES) }
        Scaffold(
            content = { paddingValues ->
                when (currentScreen) {
                    ScreenId.CATEGORIES -> {
                        CategoriesScreen(
                            modifier = Modifier.padding(paddingValues),
                        ) {

                        }
                    }

                    ScreenId.FAVORITES -> {
                        FavoritesScreen(
                            modifier = Modifier.padding(paddingValues)

                        )
                    }

                    ScreenId.RECIPES -> {
                        RecipesScreen(
                            modifier = Modifier.padding(paddingValues)
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