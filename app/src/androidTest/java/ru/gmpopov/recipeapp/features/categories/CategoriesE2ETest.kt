package ru.gmpopov.recipeapp.features.categories

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.gmpopov.recipeapp.MainActivity
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.database.entity.CategoryEntity
import ru.gmpopov.recipeapp.features.recipes.RecipesComposeScreen
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CategoriesE2ETest() : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var database: RecipesDatabase

    @Inject
    lateinit var apiService: RecipesApiService

    @Before
    fun setup() {
        hiltRule.inject()
        val dao = database.categoryDao()
        runBlocking {
            dao.insertOrUpdateCategory(
                listOf(
                    CategoryEntity(
                        id = 1,
                        name = "Бургеры",
                        description = "Бургеры",
                        imageUrl = "burgers.jpg"
                    )
                )
            )
        }

        coEvery { apiService.getRecipesByCategory(any()) } returns emptyList()
    }

    @Test
    fun categoriesScreenLoadsContent() = run {
        step("Открыть приложение и проверить экран категорий") {
            onComposeScreen<CategoriesComposeScreen>(composeTestRule) {
                categoriesGrid { assertIsDisplayed() }
            }
        }
    }

    @Test
    fun clickingCategoryOpensRecipesScreen() = run {
        step("Дождаться загрузки категорий") {
            onComposeScreen<CategoriesComposeScreen>(composeTestRule) {
                categoriesGrid { isDisplayed() }
            }
        }

        step("Нажать на первую категорию") {
            onComposeScreen<CategoriesComposeScreen>(composeTestRule) {
                categoryItem { performClick() }
            }
        }

        step("Проверить, что открылся экран рецептов") {
            onComposeScreen<RecipesComposeScreen>(composeTestRule) {
                emptyState { assertIsDisplayed() }
            }
        }
    }
}
