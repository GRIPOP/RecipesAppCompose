package ru.gmpopov.recipeapp.features.categories.ui

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
import ru.gmpopov.recipeapp.core.ui.RecipeImage
import ru.gmpopov.recipeapp.features.categories.presentation.model.CategoryUiModel
import ru.gmpopov.recipeapp.core.ui.theme.Dimens

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
        RecipeImage(
            imageUrl = category.imageUrl,
            contentDescription = category.title,
            modifier = Modifier
                .aspectRatio(Dimens.CategoryImageAspectRatio),
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
