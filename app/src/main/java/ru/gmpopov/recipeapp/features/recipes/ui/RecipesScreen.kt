package ru.gmpopov.recipeapp.features.recipes.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.features.recipes.presentation.RecipesViewModel
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel

@Composable
fun RecipesScreen(
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: RecipesViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }

        uiState.error != null ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error ?: "",
                    color = Color.Red,
                )
            }

        uiState.isEmptyRecipes ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Список рецептов пуст"
                )
            }

        else -> {
            Column(
                modifier = modifier,
            ) {
                ScreenHeader(
                    imageUrl = uiState.categoryImageUrl,
                    contentDescription = uiState.categoryTitle,
                    title = uiState.categoryTitle,
                    onShareClick = {},
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(uiState.recipes, key = { it.id }) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onClick = { onRecipeClick(recipe.id, recipe) },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
