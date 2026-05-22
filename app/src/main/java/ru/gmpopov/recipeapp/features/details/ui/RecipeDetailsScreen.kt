package ru.gmpopov.recipeapp.features.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.core.utils.MAX_PORTIONS
import ru.gmpopov.recipeapp.core.utils.MIN_PORTIONS
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel
import ru.gmpopov.recipeapp.core.ui.theme.Dimens
import ru.gmpopov.recipeapp.features.details.presentation.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
    onFavoriteToggle: (Boolean) -> Unit = {},
) {
    val viewModel: RecipeDetailsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(recipe) {
        viewModel.initializeWithRecipe(recipe)
    }

    val portionsText = pluralStringResource(
        R.plurals.portions_count,
        uiState.servings,
        uiState.servings
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenHeader(
            imagePainter = rememberAsyncImagePainter(recipe.imageUrl),
            contentDescription = recipe.title,
            title = recipe.title,
            showShareButton = true,
            onShareClick = { shareRecipe(context, recipe.id, recipe.title) },
            isFavorite = uiState.isFavorite,
            showFavoriteButton = true,
            onFavoriteClick = {
                coroutineScope.launch {
                    viewModel.toggleFavorite()
                }
                onFavoriteToggle(!uiState.isFavorite)
            },
        )

        Column(
            modifier = Modifier
                .padding(Dimens.PaddingMain)
        ) {
            Text(
                text = "Ингредиенты".uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = portionsText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            PortionsSlider(
                currentPortions = uiState.servings,
                onPortionsChanged = { newValue -> viewModel.updatePortions(newValue) },
                minPortions = MIN_PORTIONS,
                maxPortions = MAX_PORTIONS,
            )
        }

        Column(
            modifier = Modifier
                .padding(Dimens.PaddingMain)
        ) {
            uiState.scaledIngredients?.forEachIndexed { index, ingredient ->
                IngredientItem(ingredient)
                if (index < (uiState.scaledIngredients?.lastIndex ?: -1)) {
                    HorizontalDivider()
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(Dimens.PaddingMain)
        ) {
            recipe.method.forEachIndexed { index, stepOfMethod ->
                Text(
                    text = "${index + 1}. $stepOfMethod"
                )
                if (index < recipe.method.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}
