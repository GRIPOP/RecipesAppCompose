package ru.gmpopov.recipeapp.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.model.CategoryDto
import ru.gmpopov.recipeapp.data.model.RecipeDto

class RecipesRepositoryImpl(private val apiService: RecipesApiService) : RecipesRepository {

    override suspend fun getCategories(): List<CategoryDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getCategories()

            } catch (e: Exception) {
                Log.e("error_category", "$e")
                return@withContext emptyList()
            }
        }
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipesByCategory(categoryId)

            } catch (e: Exception) {
                Log.e("error_recipes", "$e")
                return@withContext emptyList()
            }
        }

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