package ru.gmpopov.recipeapp.ui.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import ru.gmpopov.recipeapp.R
import ru.gmpopov.recipeapp.core.ui.ScreenHeader
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryStub.getRecipesByCategoryId
import ru.gmpopov.recipeapp.ui.recipes.model.RecipeUiModel
import ru.gmpopov.recipeapp.ui.recipes.model.toUiModel

@Composable
fun RecipesScreen(
    categoryId: Int,
    categoryTitle: String,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    var recipes by remember { mutableStateOf<List<RecipeUiModel>>(emptyList()) }

    LaunchedEffect(categoryId) {
        recipes = getRecipesByCategoryId(categoryId)
            .map { dto -> dto.toUiModel() }
    }

    Column(
        modifier = modifier,
    ) {
        ScreenHeader(
            imagePainter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = categoryTitle,
            title = categoryTitle,
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(recipes, key = { it.id }) { recipe ->
                RecipeItem(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id, recipe) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}
