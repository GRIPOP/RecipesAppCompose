package ru.gmpopov.recipeapp.data.repository

import ru.gmpopov.recipeapp.data.model.CategoryDto
import ru.gmpopov.recipeapp.data.model.RecipeDto

interface RecipesRepository {
    suspend fun getCategories(): List<CategoryDto>

    suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto>

    suspend fun getRecipe(recipeId: Int): RecipeDto
}