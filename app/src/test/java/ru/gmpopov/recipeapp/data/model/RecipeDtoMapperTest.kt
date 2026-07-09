package ru.gmpopov.recipeapp.data.model

import junit.framework.TestCase.assertEquals
import org.junit.Test
import ru.gmpopov.recipeapp.core.utils.IMAGES_BASE_URL
import ru.gmpopov.recipeapp.features.recipes.presentation.model.toUiModel
import ru.gmpopov.recipeapp.fixtures.RecipeTestFixtures

class RecipeDtoMapperTest {

    @Test
    fun `maps DTO to UI model correctly`() {
        val recipeDto = RecipeTestFixtures.createRecipeDto()

        val result = recipeDto.toUiModel()
        assertEquals(1, result.id)
        assertEquals("Бургер", result.title)
        assertEquals("${IMAGES_BASE_URL}burger.png", result.imageUrl)
    }

    @Test
    fun `prepends base url to relative imageUrl`() {

        val recipeDto = RecipeTestFixtures.createRecipeDto()

        val result = recipeDto.toUiModel()

        assertEquals(IMAGES_BASE_URL + "burger.png", result.imageUrl)
    }

    @Test
    fun `preserves full imageUrl starting with http`() {
        val fullUrl = "https://example.com/burger.png"
        val recipeDto =
            RecipeTestFixtures.createRecipeDto(imageUrl = fullUrl)

        val result = recipeDto.toUiModel()

        assertEquals(fullUrl, result.imageUrl)
    }
}