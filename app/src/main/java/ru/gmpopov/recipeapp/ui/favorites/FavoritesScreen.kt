package ru.gmpopov.recipeapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.flow.map
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.ui.recipes.RecipeItem
import ru.gmpopov.recipeapp.util.FavoriteDataStoreManager

@Composable
fun FavoritesScreen(
    recipesRepository: RecipesRepositoryStub,
    favoriteDataStoreManager: FavoriteDataStoreManager,
    onClickRecipeCard: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoritesRecipes by remember(favoriteDataStoreManager, recipesRepository) {
        favoriteDataStoreManager.getFavoriteIdsFlow().map { favoriteIds ->
            favoriteIds.mapNotNull { favoriteId ->
                favoriteId.toIntOrNull()
            }.mapNotNull {
                recipesRepository.getRecipeById(it)
            }
        }
    }.collectAsState(initial = emptyList())

    Column(
        modifier = modifier,
    ) {
        ScreenHeader(
            painterResource(R.drawable.bcg_favorites),
            "",
            "Избранное",
            onShareClick = {},
        )

        if (favoritesRecipes.isEmpty()) {
            Text(text = "Нет избранных рецептов")
        } else {

            LazyColumn {
                items(favoritesRecipes) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onClick = onClickRecipeCard,
                    )
                }
            }
        }
    }
}