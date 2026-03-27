package ru.gmpopov.recipeapp.data.repository

import ru.gmpopov.recipeapp.data.model.CategoryDto

object RecipesRepositoryStub {

    private val categories = listOf(
        CategoryDto(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "burger.png"
        ),
        CategoryDto(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.png"
        ),
        CategoryDto(
            2,
            "Пицца",
            "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            "pizza.png"
        ),
        CategoryDto(
            3,
            "Рыба",
            "Печеная, жареная, сушеная, любая рыба на твой вкус",
            "fish.png"
        ),
        CategoryDto(
            4,
            "Супы",
            "От классики до экзотики: мир в одной тарелке",
            "soup.png"
        ),
        CategoryDto(
            5,
            "Салаты",
            "Хрустящий калейдоскоп под соусом вдохновения",
            "salad.png"
        ),
    )

    private val burgerRecipes = listOf(
        "Классический гамбургер",
        "Чизбургер",
        "Бургер с грибами и сыром",
        "Бургер с курицей и авокадо",
        "Бургер с рыбой",
        "Бургер с беконом",
        "Веганский бургер",
        "Острый гамбургер"
    )

    fun getCategories(): List<RecipeDto> {
        TODO()
    }

    fun getRecipesByCategories(): List<RecipeDto> {
        TODO()
    }
}