package ru.gmpopov.recipeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import coil.compose.rememberAsyncImagePainter
import ru.gmpopov.recipeapp.MAX_PORTIONS
import ru.gmpopov.recipeapp.MIN_PORTIONS
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.shareRecipe
import ru.gmpopov.recipeapp.ui.recipes.model.RecipeUiModel
import ru.gmpopov.recipeapp.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    showFavoriteButton: Boolean = true,
) {
    var currentPortions by remember { mutableIntStateOf(recipe.servings) }
    val context = LocalContext.current
    var isFavoriteState by rememberSaveable { mutableStateOf(false) }

    val scaleIngredients = remember(currentPortions) {
        val multiplier = currentPortions.toFloat() / recipe.servings
        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                amount = if (ingredient.amount.toFloatOrNull() == null) {
                    ingredient.amount
                } else {
                    (ingredient.amount.toFloat() * multiplier).toString()
                }
            )
        }
    }

    val portionsText = pluralStringResource(
        R.plurals.portions_count,
        currentPortions,
        currentPortions
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
            isFavorite = isFavoriteState,
            showFavoriteButton = true,
            onFavoriteClick = { isFavoriteState = !isFavoriteState},
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
                currentPortions = currentPortions,
                onPortionsChanged = { newValue -> currentPortions = newValue },
                minPortions = MIN_PORTIONS,
                maxPortions = MAX_PORTIONS,
            )
        }


        Column(
            modifier = Modifier
                .padding(Dimens.PaddingMain)
        ) {
            scaleIngredients.forEachIndexed { index, ingredient ->
                IngredientItem(ingredient)
                if (index < scaleIngredients.lastIndex) {
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

