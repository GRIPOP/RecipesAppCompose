package ru.gmpopov.recipeapp.features.favorites.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.features.favorites.presentation.FavoritesViewModel
import ru.gmpopov.recipeapp.features.recipes.ui.RecipeItem

@Composable
fun FavoritesScreen(
    onClickRecipeCard: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
    val uiState by favoritesViewModel.uiState.collectAsState()

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
