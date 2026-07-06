package ru.gmpopov.recipeapp.data.model

import org.junit.Test
import ru.gmpopov.recipeapp.features.categories.presentation.model.toUiModel
import org.junit.Assert.assertEquals

class CategoryDtoTest {
    @Test
    fun `converts DTO to UI model`() {

        val categoryDto =
            CategoryDto(
                id = 1,
                title = "pizza",
                description = "description of pizza",
                imageUrl = "pizza.png"
            )

        val result = categoryDto.toUiModel()
        assertEquals("pizza", result.title)
    }
}
