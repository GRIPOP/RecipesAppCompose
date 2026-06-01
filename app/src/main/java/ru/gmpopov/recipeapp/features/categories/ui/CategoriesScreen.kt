package ru.gmpopov.recipeapp.features.categories.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.core.ui.theme.Dimens
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.categories.presentation.CategoriesViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit,
    repository: RecipesRepository,
) {
    val viewModel: CategoriesViewModel = remember { CategoriesViewModel(repository) }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
    ) {
        ScreenHeader(
            painterResource(R.drawable.bcg_categories),
            "",
            "Категории",
            onShareClick = {},
        )

        when {
            uiState.isLoading ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }

            uiState.error != null ->
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error ?: "",
                        color = Color.Red,
                    )
                }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(Dimens.PaddingMain),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
                    verticalArrangement = Arrangement.spacedBy(Dimens.PaddingMain),
                ) {
                    items(uiState.categories, key = { it.id }) { category ->
                        CategoryItem(
                            category = category,
                            onClick = {
                                onCategoryClick(
                                    category.id,
                                    category.title,
                                    category.imageUrl,
                                )
                            },
                        )
                    }
                }
            }
        }
    }
}