package ru.gmpopov.recipeapp.features.categories.presentation.model

import androidx.compose.runtime.Immutable
import ru.gmpopov.recipeapp.core.utils.IMAGES_BASE_URL
import ru.gmpopov.recipeapp.data.model.CategoryDto

@Immutable
data class CategoryUiModel(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String,
)

fun CategoryDto.toUiModel() = CategoryUiModel(
    id = id,
    title = title,
    description = description,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else IMAGES_BASE_URL + imageUrl,
)