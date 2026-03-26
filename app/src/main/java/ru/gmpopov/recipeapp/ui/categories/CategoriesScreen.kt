package ru.gmpopov.recipeapp.ui.categories

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.ui.components.ScreenHeader

@Composable
fun CategoriesScreen() {
    Column {
        ScreenHeader(
            painterResource(R.drawable.ic_launcher_foreground),
            "",
            "Категории"
        )
        Text("Список категорий")
    }

}