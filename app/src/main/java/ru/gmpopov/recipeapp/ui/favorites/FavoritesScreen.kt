package ru.gmpopov.recipeapp.ui.favorites

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader

@Composable
fun FavoritesScreen() {
    ScreenHeader(
        painterResource(R.drawable.bcg_favorites),
        "",
        "Избранное"
    )
}