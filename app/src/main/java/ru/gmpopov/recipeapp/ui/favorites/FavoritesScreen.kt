package ru.gmpopov.recipeapp.ui.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        ScreenHeader(
            painterResource(R.drawable.bcg_favorites),
            "",
            "Избранное",
            onShareClick = {},
        )

        Text("Здесь будет список избранного")
    }
}