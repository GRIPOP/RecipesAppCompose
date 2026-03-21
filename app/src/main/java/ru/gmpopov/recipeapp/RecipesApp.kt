package ru.gmpopov.recipeapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.gmpopov.recipeapp.ui.theme.RecipeAppTheme

@Preview
@Composable
fun RecipesApp() {
    RecipeAppTheme {
        Scaffold { paddingValues ->
            Text(
                text = "RecipeApp",
                modifier = Modifier
                    .padding(paddingValues),
            )
        }
    }
}