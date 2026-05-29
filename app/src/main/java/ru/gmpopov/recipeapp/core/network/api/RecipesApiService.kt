package ru.gmpopov.recipeapp.core.network.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.gmpopov.recipeapp.data.model.CategoryDto
import ru.gmpopov.recipeapp.data.model.RecipeDto

interface RecipesApiService {
    @GET("category")
    suspend fun getCategories(): List<CategoryDto>

    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategory(@Path("id") categoryId: Int): List<RecipeDto>
}