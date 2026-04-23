package ru.gmpopov.recipeapp.core.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.ui.theme.Dimens

@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String,
    showShareButton: Boolean = false,
    onShareClick: () -> Unit = {},
    isFavorite: Boolean = false,
    showFavoriteButton: Boolean = false,
    onFavoriteClick: () -> Unit = {},
) {

    Box(
        modifier = Modifier
            .height(Dimens.HeaderHeight)
            .fillMaxWidth()
    ) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(title)
        }

        if (showShareButton) {
            IconButton(
                onClick = onShareClick,
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        if (showFavoriteButton) {
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.align(Alignment.TopStart),
            ) {
                Crossfade(
                    targetState = isFavorite,
                    animationSpec = tween(durationMillis = 300),
                    label = "Favorite"
                ) { isCurrentlyFavorite ->
                    val heartIcon = rememberVectorPainter(
                        image = ImageVector.vectorResource(
                            id = if (isCurrentlyFavorite) R.drawable.heart else R.drawable.heart_empty
                        )
                    )
                    Icon(
                        painter = heartIcon,
                        contentDescription = "Favorite",
                        tint = Color.Unspecified,
                    )
                }
            }

        }
    }
}

