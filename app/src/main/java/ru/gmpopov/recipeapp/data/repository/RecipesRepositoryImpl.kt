package ru.gmpopov.recipeapp.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.model.CategoryDto
import ru.gmpopov.recipeapp.data.model.RecipeDto
import ru.gmpopov.recipeapp.data.model.toDto
import ru.gmpopov.recipeapp.data.model.toEntity

class RecipesRepositoryImpl(
    private val apiService: RecipesApiService,
    database: RecipesDatabase,
) : RecipesRepository {

    private val categoryDao = database.categoryDao()
    private val recipeDao = database.recipeDao()

    override fun getCategories(): Flow<List<CategoryDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = apiService.getCategories().map { it.toEntity() }
                categoryDao.insertOrUpdateCategory(categories)
            } catch (e: Exception) {
                Log.e("error_loading_categories", "$e")
            }
        }
        return categoryDao.getAllCategories().map { it -> it.map { it.toDto() } }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recipes = apiService.getRecipesByCategory(categoryId).map { it.toEntity(categoryId) }
                recipeDao.insertRecipes(recipes)
            } catch (e: Exception) {
                Log.e("error_loading_recipes", "$e")
            }
        }
        return recipeDao.getAllRecipes(categoryId).map { it -> it.map { it.toDto() } }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipe(recipeId)

            } catch (e: Exception) {
                Log.e("error_recipe", "$e")
                throw e
            }
        }
    }
}