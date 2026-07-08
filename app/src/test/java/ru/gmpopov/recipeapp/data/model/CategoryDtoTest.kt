package ru.gmpopov.recipeapp.data.model

import org.junit.Test
import ru.gmpopov.recipeapp.features.categories.presentation.model.toUiModel
import org.junit.Assert.assertEquals
import ru.gmpopov.recipeapp.fixtures.CategoryTestFixtures

class CategoryDtoTest {

    @Test
    fun `mapper maps empty title correctly`() {
        val categoryDto = CategoryTestFixtures.createCategoryDto(title =  "")

        val result = categoryDto.toUiModel()

        assertEquals("", result.title)
    }

    @Test
    fun `mapper preserves very long description`() {
        val longDescription = "word".repeat(100)
        val categoryDto = CategoryTestFixtures.createCategoryDto(description = longDescription )

        val result = categoryDto.toUiModel()

        assertEquals(longDescription, result.description)
    }
}
