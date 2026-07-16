package ru.gmpopov.recipeapp.features.recipes.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.gmpopov.recipeapp.features.recipes.presentation.model.IngredientUiModel
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipeUiModel
import ru.gmpopov.recipeapp.features.recipes.presentation.model.RecipesUiState

@RunWith(AndroidJUnit4::class)
class RecipesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            RecipesContent(
                RecipesUiState(
                    categoryTitle = "Бургеры",
                    categoryImageUrl = "burger.jpg",
                    isLoading = true,
                ),
                onRecipeClick = { _, _ -> },
                modifier = Modifier,
            )
        }

        composeTestRule
            .onNodeWithTag("loading_indicator")
            .assertIsDisplayed()
    }

    @Test
    fun showsErrorState() {
        composeTestRule.setContent {
            RecipesContent(
                RecipesUiState(
                    categoryTitle = "Бургеры",
                    categoryImageUrl = "burger.jpg",
                    error = "Network error",
                ),
                onRecipeClick = { _, _ -> },
                modifier = Modifier,
            )
        }

        composeTestRule
            .onNodeWithTag("error_message")
            .assertIsDisplayed()
    }

    @Test
    fun showsEmptyState() {
        composeTestRule.setContent {
            RecipesContent(
                RecipesUiState(
                    categoryTitle = "Бургеры",
                    categoryImageUrl = "burger.jpg",
                ),
                onRecipeClick = { _, _ -> },
                modifier = Modifier,
            )
        }

        composeTestRule
            .onNodeWithTag("empty_state")
            .assertIsDisplayed()
    }

    @Test
    fun displaysRecipeList() {
        composeTestRule.setContent {
            RecipesContent(
                RecipesUiState(
                    categoryTitle = "Бургеры",
                    categoryImageUrl = "burger.jpg",
                    recipes =
                        listOf(
                            RecipeUiModel(
                                id = 1,
                                title = "Бургер с грибами".uppercase(),
                                ingredients = listOf(
                                    IngredientUiModel(
                                        name = "Булочка",
                                        amount = "2",
                                        unit = "шт"
                                    )
                                ),
                                method = listOf(),
                                imageUrl = "burger.jpg",
                                isFavorite = false,
                                servings = 4,
                            )
                        ),
                ),
                onRecipeClick = { _, _ -> },
                modifier = Modifier,
            )
        }

        composeTestRule
            .onNodeWithText("БУРГЕР С ГРИБАМИ")
            .assertIsDisplayed()
    }
}