package ru.gmpopov.recipeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.gmpopov.recipeapp.cori.ui.navigation.BottomNavigation
import ru.gmpopov.recipeapp.ui.theme.RecipeAppTheme

@Preview
@Composable
fun RecipesApp() {
    RecipeAppTheme {
        var currentScreen by remember { mutableStateOf(ScreenId.CATEGORIES) }
        Scaffold(
            content = { paddingValues ->
                when (currentScreen) {
                    ScreenId.CATEGORIES -> {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()
                            ) {
                            Text(
                                text = "Категории",
                            )
                        }
                    }

                    ScreenId.FAVORITES -> {
                        Box(contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(paddingValues)
                                .fillMaxSize()) {
                            Text(
                                text = "Избранное",
                            )
                        }
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