package ru.gmpopov.recipeapp.features.favorites.ui

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.di.FavoritesViewModelFactory
import ru.gmpopov.recipeapp.features.recipes.ui.RecipeItem

@Composable
fun FavoritesScreen(
    onClickRecipeCard: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel = remember { FavoritesViewModelFactory(application).create() }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier,
    ) {
        ScreenHeader(
            painterResource(R.drawable.bcg_favorites),
            "",
            "Избранное",
            onShareClick = {},
        )

        if (uiState.favoriteRecipes.isEmpty()) {
            Text(text = "Нет избранных рецептов")
        } else {

            LazyColumn {
                items(items = uiState.favoriteRecipes, key = { it.id }) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onClick = onClickRecipeCard,
                    )
                }
            }
        }
    }
}