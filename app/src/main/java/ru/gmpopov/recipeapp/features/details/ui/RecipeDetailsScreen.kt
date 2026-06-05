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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import coil.compose.rememberAsyncImagePainter
import ru.gmpopov.recipeapp.core.utils.MAX_PORTIONS
import ru.gmpopov.recipeapp.core.utils.MIN_PORTIONS
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.core.ui.theme.Dimens
import ru.gmpopov.recipeapp.features.details.presentation.RecipeDetailsViewModel

@Composable
fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    onFavoriteToggle: (Boolean) -> Unit = {},
    viewModel: RecipeDetailsViewModel,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
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
            imagePainter = rememberAsyncImagePainter(uiState.recipe?.imageUrl ?: ""),
            contentDescription = uiState.recipe?.title ?: "",
            title = uiState.recipe?.title ?: "",
            showShareButton = true,
            onShareClick = {
                uiState.recipe?.let { recipe ->
                    shareRecipe(
                        context = context,
                        recipeId = recipe.id,
                        recipeTitle = recipe.title,
                    )
                }
            },
            isFavorite = uiState.isFavorite,
            showFavoriteButton = true,
            onFavoriteClick = {
                viewModel.toggleFavorite()
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
            uiState.scaledIngredients?.let { ingredients ->
                ingredients.forEachIndexed { index, ingredient ->
                    IngredientItem(ingredient)
                    if (index < (ingredients.lastIndex)) {
                        HorizontalDivider()
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(Dimens.PaddingMain)
        ) {
            uiState.recipe?.let { recipe ->
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
}
