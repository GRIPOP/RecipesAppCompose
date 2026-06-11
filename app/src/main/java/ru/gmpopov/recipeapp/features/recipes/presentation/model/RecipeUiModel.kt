package ru.gmpopov.recipeapp.features.recipes.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.compose.runtime.Immutable
import ru.gmpopov.recipeapp.core.utils.IMAGES_BASE_URL
import ru.gmpopov.recipeapp.data.model.RecipeDto

@Parcelize
@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean,
    val servings: Int,
    ) : Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else IMAGES_BASE_URL + imageUrl,
    isFavorite = false,
    servings = servings,
)