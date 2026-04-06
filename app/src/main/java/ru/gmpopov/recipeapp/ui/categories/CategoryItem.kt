package ru.gmpopov.recipeapp.ui.categories

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.ui.categories.model.CategoryUiModel
import ru.gmpopov.recipeapp.ui.theme.Dimens


@Composable
fun CategoryItem(
    category: CategoryUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(Dimens.СornerRadius),
        elevation = CardDefaults.cardElevation(Dimens.CardElevation),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = category.imageUrl,
            contentDescription = category.title,
            modifier = Modifier
                .aspectRatio(Dimens.CategoryImageAspectRatio),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            error = painterResource(R.drawable.ic_launcher_foreground)

        )

        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.PaddingMedium, vertical = Dimens.PaddingMedium),
            text = category.title.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            modifier = Modifier
                .padding(horizontal = Dimens.PaddingMedium, vertical = Dimens.PaddingMedium)
                .height(Dimens.CategoryDescriptionHeight),
            text = category.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 3
        )
    }
}
