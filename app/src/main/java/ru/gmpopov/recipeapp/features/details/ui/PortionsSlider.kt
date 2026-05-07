package ru.gmpopov.recipeapp.features.details.ui

import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import kotlin.math.roundToInt

@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChanged: (Int) -> Unit,
    minPortions: Float,
    maxPortions: Float,
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChanged(it.roundToInt()) },
        valueRange = minPortions..maxPortions,
        steps = (maxPortions - minPortions - 1).toInt(),
    )
}