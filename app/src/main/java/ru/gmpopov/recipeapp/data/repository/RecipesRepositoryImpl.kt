package ru.gmpopov.recipeapp.data.repository

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.model.CategoryDto
import ru.gmpopov.recipeapp.data.model.RecipeDto
import ru.gmpopov.recipeapp.data.model.toDto
import ru.gmpopov.recipeapp.data.model.toEntity
import javax.inject.Inject

class RecipesRepositoryImpl @Inject constructor(
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
                val recipes =
                    apiService.getRecipesByCategory(categoryId).map { it.toEntity(categoryId) }
                recipeDao.insertRecipes(recipes)
            } catch (e: Exception) {
                Log.e("error_loading_recipes", "$e")
            }
        }
        return recipeDao.getAllRecipes(categoryId).map { it -> it.map { it.toDto() } }
    }

    override fun getRecipe(recipeId: Int): Flow<RecipeDto?> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                apiService.getRecipe(recipeId).let { recipe ->
                    recipeDao.insertRecipes(listOf(recipe.toEntity(recipeId)))
                }

            } catch (e: Exception) {
                Log.e("error_loading_recipe", "$e")
            }
        }
        return recipeDao.getRecipe(recipeId).map { entity -> entity?.toDto() }
    }
}