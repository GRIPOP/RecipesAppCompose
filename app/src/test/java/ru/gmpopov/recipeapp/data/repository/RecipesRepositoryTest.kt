package ru.gmpopov.recipeapp.data.repository

import app.cash.turbine.test
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.database.dao.CategoryDao
import ru.gmpopov.recipeapp.data.database.dao.RecipeDao
import ru.gmpopov.recipeapp.data.database.entity.CategoryEntity
import ru.gmpopov.recipeapp.data.database.entity.RecipeEntity


class RecipesRepositoryTest {

    private val apiService = mockk<RecipesApiService>()
    private val database = mockk<RecipesDatabase>()
    private val categoryDao = mockk<CategoryDao>()
    private val recipeDao = mockk<RecipeDao>()

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setup() {
        every { database.categoryDao() } returns categoryDao
        every { database.recipeDao() } returns recipeDao
        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCategories emits categories from database`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(
                CategoryEntity(
                    id = 1,
                    name = "Бургеры",
                    description = "Рецепты бургеров",
                    imageUrl = "burgers.png",
                ),
            )
        )

        coEvery { apiService.getCategories() } returns emptyList()
        coEvery { categoryDao.insertOrUpdateCategory(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()
            assertEquals(1, categories.size)
            assertEquals("Бургеры", categories[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCategories still emits data when api throws exception`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(
            listOf(
                CategoryEntity(
                    id = 1,
                    name = "Бургеры",
                    description = "Рецепты бургеров",
                    imageUrl = "burgers.png",
                )
            )
        )

        coEvery { apiService.getCategories() } throws (RuntimeException("Network error"))
        coEvery { categoryDao.insertOrUpdateCategory(any()) } just Runs

        repository.getCategories().test {
            val categories = awaitItem()
            assertEquals(1, categories.size)
            assertEquals("Бургеры", categories[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getRecipesByCategory returns flow filtered by categoryId`() = runTest {
        every { recipeDao.getAllRecipes(1) } returns flowOf(
            listOf(
                RecipeEntity(
                    id = 1,
                    title = "Чизбургер",
                    categoryId = 1,
                    imageUrl = "cheeseburger.png",
                    ingredients = """[{"quantity":"1 шт","unitOfMeasure":"шт","description":"Котлета"},{"quantity":"1","unitOfMeasure":"ломтик","description":"Сыр"}]""",
                    method = """["Обжарить котлету","Собрать бургер","Подавать горячим"]"""
                ),
                RecipeEntity(
                    id = 2,
                    title = "Бургер с грибами",
                    categoryId = 1,
                    imageUrl = "burger with mushrooms.png",
                    ingredients = """[{"quantity":"1 шт","unitOfMeasure":"шт","description":"Котлета"},{"quantity":"2","unitOfMeasure":"шт","description":"Булочки"}]""",
                    method = """["Обжарить котлету","Собрать бургер","Подавать горячим"]"""
                )
            )
        )

        coEvery { apiService.getRecipesByCategory(1) } returns emptyList()
        coEvery { recipeDao.insertRecipes(any()) } just Runs

        repository.getRecipesByCategory(1).test {
            val recipes = awaitItem()
            assertEquals(2, recipes.size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
