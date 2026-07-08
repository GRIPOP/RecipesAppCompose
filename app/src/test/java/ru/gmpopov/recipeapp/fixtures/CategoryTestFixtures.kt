package ru.gmpopov.recipeapp.fixtures

import ru.gmpopov.recipeapp.data.model.CategoryDto

object CategoryTestFixtures {

    fun createCategoryDto(
        id: Int = 1,
        title: String = "Бургеры",
        description: String = "Бургеры",
        imageUrl: String = "burgers.png",
    ) = CategoryDto(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
    )

    fun createCategoryDtoList(count: Int = 3) = List(count) { index ->
        createCategoryDto(id = index + 1, title = "Recipe ${index + 1}")
    }
}