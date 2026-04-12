package ru.gmpopov.recipeapp.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.ui.recipes.model.RecipeUiModel
import ru.gmpopov.recipeapp.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        ScreenHeader(
            imagePainter = rememberAsyncImagePainter(recipe.imageUrl),
            contentDescription = recipe.title,
            title = recipe.title,
        )
        Column(
            modifier = Modifier
                .padding(Dimens.PaddingMain)
        ) {
            recipe.ingredients.forEachIndexed { index, ingredient ->
                IngredientItem(ingredient)
                if (index < recipe.ingredients.lastIndex) {
                    HorizontalDivider()
                }
            }
        }

        Column(
            modifier = Modifier
                .padding(Dimens.PaddingMain)
        ) {
            recipe.method.forEachIndexed { index, stepOfMethod ->
                Row {
                    Text(
                        text = stepOfMethod
                    )
                }
                if (index < recipe.method.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

