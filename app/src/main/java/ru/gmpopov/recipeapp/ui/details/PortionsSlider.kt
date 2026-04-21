package ru.gmpopov.recipeapp.ui.details

import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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