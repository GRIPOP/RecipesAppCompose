package ru.gmpopov.recipeapp.features.categories.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import ru.gmpopov.recipeapp.features.categories.presentation.model.CategoriesUiState
import ru.gmpopov.recipeapp.features.categories.presentation.model.CategoryUiModel


class CategoriesContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displayCategories() {
        composeTestRule.setContent {
            CategoriesContent(
                CategoriesUiState(
                    listOf(
                        CategoryUiModel(
                            id = 1,
                            title = "Бургеры",
                            description = "Бургеры",
                            "burger.jpg"
                        )
                    )
                ), { _, _, _ -> },
                modifier = Modifier
            )
        }

        composeTestRule
            .onNodeWithText("БУРГЕРЫ")
            .assertIsDisplayed()
    }

    @Test
    fun clickingCategoryNavigatesToRecipes() {
        var clickedId: Int? = null
        composeTestRule.setContent {
            CategoriesContent(
                CategoriesUiState(
                    listOf(
                        CategoryUiModel(
                            id = 1,
                            title = "Бургеры",
                            description = "Бургеры",
                            "burger.jpg"
                        )
                    )
                ), onCategoryClick = { id, _, _ -> clickedId = id },
                modifier = Modifier
            )
        }

        composeTestRule
            .onNodeWithText("БУРГЕРЫ")
            .performClick()

        assertEquals(1, clickedId)
    }

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            CategoriesContent(
                CategoriesUiState(
                    isLoading = true,
                ),
                onCategoryClick = { _, _, _ -> },
                modifier = Modifier,
            )
        }

        composeTestRule
            .onNodeWithTag("loading_indicator")
            .assertIsDisplayed()
    }
}
