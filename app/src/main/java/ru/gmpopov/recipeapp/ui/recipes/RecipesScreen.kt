package ru.gmpopov.recipeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "",
            title = "Название блюда"
        )

        Text("Скоро здесь будет список рецептов")
    }
}