package ru.gmpopov.recipeapp.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import ru.gmpopov.recipeapp.data.database.entity.RecipeEntity

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
    val servings: Int = 1,
)

fun RecipeDto.toEntity(categoryId: Int) = RecipeEntity (
    id = id,
    title = title,
    ingredients = Json.encodeToString(ingredients),
    method = Json.encodeToString(method),
    imageUrl = imageUrl,
    categoryId =  categoryId,
)

fun RecipeEntity.toDto() = RecipeDto(
    id = id,
    title = title,
    ingredients = Json.decodeFromString<List<IngredientDto>>(ingredients),
    method = Json.decodeFromString<List<String>>(method),
    imageUrl = imageUrl,
)
