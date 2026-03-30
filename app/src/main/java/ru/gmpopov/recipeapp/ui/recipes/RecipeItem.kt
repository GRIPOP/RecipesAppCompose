package ru.gmpopov.recipeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.ui.recipes.model.RecipeUiModel
import ru.gmpopov.recipeapp.ui.theme.Dimens

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
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                placeholder = painterResource(R.drawable.ic_launcher_background),
                error = painterResource(R.drawable.ic_launcher_foreground),
                contentScale = ContentScale.Crop,
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