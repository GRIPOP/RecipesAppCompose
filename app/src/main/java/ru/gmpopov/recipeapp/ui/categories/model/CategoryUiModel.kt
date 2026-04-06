package ru.gmpopov.recipeapp.ui.categories.model

import androidx.compose.runtime.Immutable
import ru.gmpopov.recipeapp.ASSETS_URI_PREFIX
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
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else ASSETS_URI_PREFIX + imageUrl,
)