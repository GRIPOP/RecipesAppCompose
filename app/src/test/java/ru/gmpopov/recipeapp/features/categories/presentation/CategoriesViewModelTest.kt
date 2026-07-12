package ru.gmpopov.recipeapp.features.categories.presentation

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
import ru.gmpopov.recipeapp.fixtures.CategoryTestFixtures
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    private val repository = mockk<RecipesRepository>()
    private lateinit var viewModel: CategoriesViewModel
    val expectedCategories = CategoryTestFixtures.createCategoryDtoList()


    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        every { repository.getCategories() } returns flowOf(expectedCategories)
        viewModel = CategoriesViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loads categories from repository`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.isLoading)
            assertEquals(expectedCategories.size, state.categories.size)
        }
    }

    @Test
    fun `shows empty list when repository returns no data`() = runTest {
        every { repository.getCategories() } returns flowOf(emptyList())
        viewModel = CategoriesViewModel(repository)
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.categories.isEmpty())
            assertTrue(state.error == null)
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest {
        every { repository.getCategories() } throws IOException()
        viewModel = CategoriesViewModel(repository)
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.error != null)
        }
    }
}