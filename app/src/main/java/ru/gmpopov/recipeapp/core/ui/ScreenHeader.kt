package ru.gmpopov.recipeapp.core.ui

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
import androidx.compose.ui.layout.ContentScale
import ru.gmpopov.recipeapp.ui.theme.Dimens

@Composable
fun ScreenHeader(
    imagePainter: Painter,
    contentDescription: String,
    title: String,
    showShareButton: Boolean = false,
    onShareClick: () -> Unit,
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
    }
}

