package ru.gmpopov.recipeapp.features.recipes.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.gmpopov.recipeapp.core.ui.RecipeImage
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel
import ru.gmpopov.recipeapp.core.ui.theme.Dimens

@Composable
fun RecipeItem(
    recipe: RecipeUiModel,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = { onClick(recipe.id) }
    ) {
        Column {
            RecipeImage(
                imageUrl = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier
                    .aspectRatio(Dimens.CategoryImageAspectRatio),
            )

            Text(
                text = recipe.title,
                modifier = Modifier
                    .padding(
                        start = Dimens.PaddingMedium,
                        top = Dimens.PaddingMedium,
                        bottom = Dimens.PaddingMedium
                    ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}