package ru.gmpopov.recipeapp.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.database.dao.CategoryDao
import ru.gmpopov.recipeapp.data.database.entity.CategoryEntity
import ru.gmpopov.recipeapp.data.model.CategoryDto


@RunWith(AndroidJUnit4::class)
class RecipesRepositoryIntegrationTest {

    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private val apiService = mockk<RecipesApiService>()

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        categoryDao = database.categoryDao()
        repository = RecipesRepositoryImpl(apiService = apiService, database = database)

    }

    @After
    fun tearDown() {
        database.close()

    }

    @Test
    fun savesDataToCacheAfterSuccessfulApiCall() = runTest {
        coEvery { apiService.getCategories() } returns
                listOf(
                    CategoryDto(
                        id = 1,
                        title = "Бургеры",
                        description = "Бургеры",
                        imageUrl = "burger.jpg"
                    )
                )
        repository.getCategories().test {
            awaitItem()
            val loadedData = awaitItem()
            assertEquals("Бургеры", loadedData.first().title)

            val cache = categoryDao.getAllCategories().first()
            assertEquals("Бургеры", cache[0].name)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun returnsCachedDataWhenApiFails() = runTest {

        val categories = listOf(
            CategoryEntity(
                id = 1,
                name = "Бургеры",
                description = "Вкусные бургеры",
                imageUrl = "burgers.jpg"
            ),
            CategoryEntity(
                id = 2,
                name = "Десерты",
                description = "Изысканные десерты на любой вкус",
                imageUrl = "deserts.jpg"
            )
        )

        categoryDao.insertOrUpdateCategory(categories)

        coEvery { apiService.getCategories() } throws IOException()

        repository.getCategories().test {
            val cache = awaitItem()
            assertEquals(2, cache.size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
