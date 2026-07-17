package ru.gmpopov.recipeapp.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.database.entity.CategoryEntity
import ru.gmpopov.recipeapp.data.database.entity.RecipeEntity

@RunWith(AndroidJUnit4::class)
class RecipesDaoTest {

    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        categoryDao = database.categoryDao()
        recipeDao = database.recipeDao()
    }


    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertsAndRetrievesCategories() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Бургеры", description = "Вкусные бургеры", imageUrl = "burgers.jpg"),
            CategoryEntity(id = 2, name = "Десерты", description = "Изысканные десерты на любой вкус", imageUrl = "deserts.jpg")
        )

        categoryDao.insertOrUpdateCategory(categories)
        val retrieves = categoryDao.getAllCategories().first()

        assertEquals(2, retrieves.size)
    }

    @Test
    fun insertReplacesDuplicateCategory() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Бургеры", description = "Вкусные бургеры", imageUrl = "burgers.jpg"),
            CategoryEntity(id = 1, name = "Десерты", description = "Изысканные десерты на любой вкус", imageUrl = "deserts.jpg")
        )

        categoryDao.insertOrUpdateCategory(categories)
        val retrieves = categoryDao.getAllCategories().first()

        assertTrue(retrieves.size == 1)
        assertEquals("Десерты", retrieves[0].name)
    }

    @Test
    fun getRecipesByCategoryReturnsCorrectItems() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Бургеры", description = "Вкусные бургеры", imageUrl = "burgers.jpg"),
            CategoryEntity(id = 2, name = "Десерты", description = "Изысканные десерты на любой вкус", imageUrl = "deserts.jpg")
        )

        categoryDao.insertOrUpdateCategory(categories)
        recipeDao.insertRecipes(listOf(
            RecipeEntity(id = 1, title = "Бургер с грибами", categoryId = 1, imageUrl = "burger_with_mushrooms", ingredients = "булочка", method = "Готовка бургера" ),
            RecipeEntity(id = 2, title = "Чизкейк", categoryId = 2, imageUrl = "cheesecake", ingredients = "Творожный сыр", method = "Смешать все ингредиенты" )


        ))
        val recipe = recipeDao.getAllRecipes(1).first()

        assertEquals("Бургер с грибами", recipe[0].title)
    }

    @Test
    fun emptyDatabaseReturnsEmptyList() = runTest {
        val categories = categoryDao.getAllCategories().first()

        assertTrue(categories.isEmpty())
    }
}
