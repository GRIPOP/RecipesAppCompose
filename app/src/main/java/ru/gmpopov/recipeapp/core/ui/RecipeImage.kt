package ru.gmpopov.recipeapp.core.ui

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.theme.Dimens

@Composable
fun RecipeImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .size(200, 200)
            .build(),
        contentDescription = contentDescription,
        loading = { CircularProgressIndicator() },
        error = { painterResource(R.drawable.ic_launcher_foreground) },
        contentScale = contentScale,
        modifier = modifier
            .aspectRatio(Dimens.CategoryImageAspectRatio),
        )
}