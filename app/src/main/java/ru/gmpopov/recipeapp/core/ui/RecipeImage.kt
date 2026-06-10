package ru.gmpopov.recipeapp.core.ui

import androidx.compose.foundation.Image
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.gmpopov.recipeapp.R

@Composable
fun RecipeImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val context = LocalContext.current

    SubcomposeAsyncImage(
        model = remember(imageUrl) {
            ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .size(200, 200)
                .build()
        },
        contentDescription = contentDescription,
        loading = { CircularProgressIndicator() },
        error = {
            Image(
                painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = null
            )
        },
        contentScale = contentScale,
        modifier = modifier,
    )
}