package ru.gmpopov.recipeapp.ui.recipes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.compose.runtime.Immutable
import ru.gmpopov.recipeapp.ASSETS_URI_PREFIX
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
    ) : Parcelable

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else ASSETS_URI_PREFIX + imageUrl,
    isFavorite = false,
)