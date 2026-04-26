package ru.gmpopov.recipeapp.ui.recipes.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.compose.runtime.Immutable
import ru.gmpopov.recipeapp.data.model.IngredientDto

@Parcelize
@Immutable
data class IngredientUiModel(
    val name: String,
    val amount: String,
    val unit: String,
) : Parcelable

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description.uppercase(),
    amount = quantity,
    unit = unitOfMeasure,
)