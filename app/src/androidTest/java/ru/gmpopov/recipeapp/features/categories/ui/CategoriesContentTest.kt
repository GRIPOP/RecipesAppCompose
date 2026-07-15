package ru.gmpopov.recipeapp.features.categories.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
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
                ), { _, _, _ -> }, Modifier
            )
        }

        composeTestRule
            .onNodeWithText("БУРГЕРЫ")
            .assertIsDisplayed()
    }
}