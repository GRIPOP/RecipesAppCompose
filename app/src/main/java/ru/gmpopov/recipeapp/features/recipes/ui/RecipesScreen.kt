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
import androidx.compose.ui.platform.testTag
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.features.recipes.presentation.RecipesViewModel
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipesUiState

@Composable
fun RecipesScreen(
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipesViewModel,
) {

    val uiState by viewModel.uiState.collectAsState()

    RecipesContent(uiState = uiState, onRecipeClick = onRecipeClick, modifier = modifier)
}

@Composable
fun RecipesContent(
    uiState: RecipesUiState,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier,
) {
    when {
        uiState.isLoading ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("recipes_screen"),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .testTag("loading_indicator")
                )
            }

        uiState.error != null ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("recipes_screen"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error,
                    color = Color.Red,
                    modifier = Modifier
                        .testTag("error_message")
                )
            }

        uiState.isEmptyRecipes ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("recipes_screen"),

                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Список рецептов пуст",
                    modifier = Modifier
                        .testTag("empty_state")
                )
            }

        else -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .testTag("recipes_screen"),
            ) {
                ScreenHeader(
                    imageUrl = uiState.categoryImageUrl,
                    contentDescription = uiState.categoryTitle,
                    title = uiState.categoryTitle,
                    onShareClick = {},
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
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
