package ru.gmpopov.recipeapp.ui.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub
import ru.gmpopov.recipeapp.ui.categories.model.toUiModel
import ru.gmpopov.recipeapp.ui.theme.Dimens

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String) -> Unit,
) {
    val categories = remember { RecipesRepositoryStub.getCategories().map { it.toUiModel() } }

    Column(
        modifier = modifier
    ) {

        ScreenHeader(
            painterResource(R.drawable.bcg_categories),
            "",
            "Категории",
            onShareClick = {}
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Dimens.PaddingMain),
            horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
            verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
        ) {
            items(categories, key = { it.id }) { category ->
                CategoryItem(
                    category = category,
                    onClick = {
                        onCategoryClick(
                            category.id,
                            category.title
                        )
                    },
                )
            }
        }
    }
}