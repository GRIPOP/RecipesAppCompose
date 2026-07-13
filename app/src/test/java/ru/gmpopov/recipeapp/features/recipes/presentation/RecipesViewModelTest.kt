package ru.gmpopov.recipeapp.features.recipes.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.fixtures.RecipeTestFixtures
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {
    val repository = mockk<RecipesRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    private fun createViewModel(
        categoryId: Int = 1,
        categoryTitle: String = "",
        categoryImageUrl: String = "",
    ) = RecipesViewModel(
        savedStateHandle = SavedStateHandle(
            mapOf(
                "categoryId" to categoryId,
                "categoryTitle" to categoryTitle,
                "categoryImageUrl" to categoryImageUrl
            )
        ),
        repository = repository,
    )

    @Test
    fun `loads recipes for category`() = runTest {
        every { repository.getRecipesByCategory(1) } returns flowOf(
            RecipeTestFixtures.createRecipeDtoList()
        )

        val viewModel = createViewModel(categoryTitle = "Бургеры", categoryImageUrl = "burger.jpg")

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(RecipeTestFixtures.createRecipeDtoList().size, state.recipes.size)
        }
    }

    @Test
    fun `shows empty list when repository returns no data`() = runTest {
        every { repository.getRecipesByCategory(1) } returns flowOf(emptyList())

        val viewModel = createViewModel(categoryTitle = "Десерты", categoryImageUrl = "desert.jpg")

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.recipes.isEmpty())
            assertTrue(state.error == null)
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        every { repository.getRecipesByCategory(1) } throws IOException()

        val viewModel = createViewModel(categoryTitle = "Десерты", categoryImageUrl = "desert.jpg")

        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.error != null)
        }
    }
}
