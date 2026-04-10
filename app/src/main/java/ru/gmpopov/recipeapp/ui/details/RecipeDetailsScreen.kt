package ru.gmpopov.recipeapp.ui.details

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.gmpopov.recipeapp.ui.recipes.model.RecipeUiModel
import ru.gmpopov.recipeapp.ui.theme.Dimens

@Composable
fun RecipeDetailsScreen(
    recipe: RecipeUiModel,
    modifier: Modifier = Modifier,
) {
    Text(
        text = recipe.title,
        style = MaterialTheme.typography.headlineMedium,
        modifier = modifier.padding(Dimens.PaddingMain)
    )

}