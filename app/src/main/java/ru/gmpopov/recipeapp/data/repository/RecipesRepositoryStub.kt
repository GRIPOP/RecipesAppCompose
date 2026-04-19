package ru.gmpopov.recipeapp.data.repository

import ru.gmpopov.recipeapp.data.model.CategoryDto
import ru.gmpopov.recipeapp.data.model.IngredientDto
import ru.gmpopov.recipeapp.data.model.RecipeDto
import ru.gmpopov.recipeapp.ui.recipes.model.RecipeUiModel
import ru.gmpopov.recipeapp.ui.recipes.model.toUiModel

object RecipesRepositoryStub {

    private val categories: List<CategoryDto> = listOf(
        CategoryDto(
            0,
            "Бургеры",
            "Рецепты всех популярных видов бургеров",
            "burger.jpg"
        ),
        CategoryDto(
            1,
            "Десерты",
            "Самые вкусные рецепты десертов специально для вас",
            "dessert.jpg"
        ),
        CategoryDto(
            2,
            "Пицца",
            "Пицца на любой вкус и цвет. Лучшая подборка для тебя",
            "pizza.jpg"
        ),
        CategoryDto(
            3,
            "Рыба",
            "Печеная, жареная, сушеная, любая рыба на твой вкус",
            "fish.jpg"
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

    private val burgerRecipes: List<RecipeDto> =
        listOf(
            RecipeDto(
                0,
                "Классический бургер с говядиной",
                listOf<IngredientDto>
                    (
                    IngredientDto(
                        "0.5",
                        "кг",
                        "говяжий фарш",
                    ),
                    IngredientDto(
                        "1.0",
                        "шт",
                        "луковица, мелко нарезанная"
                    ),
                    IngredientDto(
                        "2.0",
                        "зубч",
                        "чеснок, измельченный",
                    ),
                    IngredientDto(
                        "4.0",
                        "шт",
                        "булочки для бургера",
                    ),
                    IngredientDto(
                        "4.0",
                        "шт",
                        "листа салата",
                    ),
                    IngredientDto(
                        "1.0",
                        "шт",
                        "помидор, нарезанный кольцами",
                    ),
                    IngredientDto(
                        "2.0",
                        "ст. л.",
                        "горчица",
                    ),
                    IngredientDto(
                        "2.0",
                        "ст. л.",
                        "кетчуп",
                    ),
                    IngredientDto(
                        "по вкусу",
                        "",
                        "соль и черный перец"
                    ),
                ),


                listOf(
                    "В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты.",
                    "Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки.",
                    "В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки.",
                    "Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки.",
                    "Подавайте бургеры горячими с картофельными чипсами или картофельным пюре.",
                ),
                "burger_hamburger.png",
            ),
            RecipeDto(
                1,
                "Чизбургер с беконом",
                listOf<IngredientDto>
                    (
                    IngredientDto(
                        "0.4",
                        "кг",
                        "говяжий фарш"
                    ),
                    IngredientDto(
                        "4.0",
                        "шт",
                        "ломтика бекона"
                    ),
                    IngredientDto(
                        "4.0",
                        "шт",
                        "ломтика сыра чеддер",
                    ),
                    IngredientDto(
                        "4.0",
                        "шт",
                        "булочки для бургера",
                    ),
                    IngredientDto(
                        "1.0",
                        "шт",
                        "помидор, нарезанный",
                    ),
                    IngredientDto(
                        "по вкусу",
                        "",
                        "майонез и кетчуп",
                    ),
                ),

                listOf(
                    "Обжарьте бекон на сковороде до хрустящей корочки, отложите на бумажное полотенце.",
                    "Сформируйте из фарша 4 котлеты, обжарьте с каждой стороны по 4 минуты.",
                    "За минуту до готовности положите на каждую котлету по ломтику сыра, чтобы он расплавился.",
                    "Соберите бургер: булочка, майонез, котлета с сыром, бекон, помидор, кетчуп.",
                    "Подавайте горячими."
                ),
                "burger_cheeseburger.png",
            ),
        )

    private val dessertRecipes: List<RecipeDto> =
        listOf(
            RecipeDto(
                id = 0,
                title = "Тирамису",
                ingredients = listOf(
                    IngredientDto("250", "г", "сыр маскарпоне"),
                    IngredientDto("3", "шт", "яйца"),
                    IngredientDto("100", "г", "сахар"),
                    IngredientDto("200", "мл", "крепкий кофе эспрессо"),
                    IngredientDto("200", "г", "печенье савоярди"),
                    IngredientDto("2", "ст. л.", "какао-порошок"),
                    IngredientDto("2", "ст. л.", "ром или амаретто (по желанию)")
                ),
                method = listOf(
                    "Отделите желтки от белков. Желтки взбейте с сахаром до получения светлой массы.",
                    "Добавьте маскарпоне к желткам и хорошо перемешайте до однородности.",
                    "Белки взбейте в крепкую пену и аккуратно вмешайте в сырную массу.",
                    "Кофе смешайте с ромом (если используете).",
                    "Быстро окуните печенье в кофе (не размачивая) и выложите слой в форму.",
                    "Сверху выложите половину крема. Повторите слой печенья и оставшегося крема.",
                    "Поставьте в холодильник минимум на 4 часа.",
                    "Перед подачей посыпьте какао-порошком."
                ),
                imageUrl = "dessert_tiramisu.png"
            ),
            RecipeDto(
                id = 1,
                title = "Шоколадный брауни",
                ingredients = listOf(
                    IngredientDto("200", "г", "темный шоколад (70%)"),
                    IngredientDto("150", "г", "сливочное масло"),
                    IngredientDto("3", "шт", "яйца"),
                    IngredientDto("200", "г", "сахар"),
                    IngredientDto("100", "г", "мука"),
                    IngredientDto("50", "г", "грецкие орехи (по желанию)"),
                    IngredientDto("1", "ч. л.", "ванильный экстракт")
                ),
                method = listOf(
                    "Разогрейте духовку до 180°C. Форму для выпечки застелите пергаментом.",
                    "Растопите шоколад и масло на водяной бане до однородности.",
                    "В отдельной миске взбейте яйца с сахаром до пышной массы.",
                    "Смешайте шоколадную смесь с яичной, добавьте ваниль.",
                    "Просейте муку и аккуратно вмешайте в тесто. Добавьте орехи.",
                    "Вылейте тесто в форму и выпекайте 20-25 минут.",
                    "Брауни должен остаться слегка влажным внутри. Остудите перед нарезкой."
                ),
                imageUrl = "dessert_brownie.png"
            ),
        )

    private val pizzaRecipes: List<RecipeDto> =
        listOf(
            RecipeDto(
                id = 0,
                title = "Маргарита",
                ingredients = listOf(
                    IngredientDto("300", "г", "тесто для пиццы"),
                    IngredientDto("200", "г", "томатный соус"),
                    IngredientDto("250", "г", "моцарелла"),
                    IngredientDto("2", "шт", "помидора"),
                    IngredientDto("несколько", "шт", "листьев базилика"),
                    IngredientDto("2", "ст. л.", "оливковое масло"),
                    IngredientDto("по вкусу", "", "соль и перец")
                ),
                method = listOf(
                    "Разогрейте духовку до 250°C (максимум).",
                    "Раскатайте тесто в тонкий круг.",
                    "Смажьте тесто томатным соусом.",
                    "Выложите нарезанную моцареллу и кружочки помидоров.",
                    "Посолите, поперчите, сбрызните оливковым маслом.",
                    "Выпекайте 10-12 минут до золотистой корочки.",
                    "Готовую пиццу украсьте листьями базилика."
                ),
                imageUrl = "pizza_margherita.png"
            ),
            RecipeDto(
                id = 1,
                title = "Пепперони",
                ingredients = listOf(
                    IngredientDto("300", "г", "тесто для пиццы"),
                    IngredientDto("200", "г", "томатный соус"),
                    IngredientDto("250", "г", "моцарелла"),
                    IngredientDto("100", "г", "пепперони"),
                    IngredientDto("2", "ст. л.", "оливковое масло"),
                    IngredientDto("1", "ч. л.", "сушеный орегано")
                ),
                method = listOf(
                    "Разогрейте духовку до 250°C.",
                    "Раскатайте тесто, смажьте томатным соусом.",
                    "Посыпьте тертой моцареллой.",
                    "Выложите кружочки пепперони.",
                    "Посыпьте орегано, сбрызните маслом.",
                    "Выпекайте 10-12 минут до хрустящей корочки."
                ),
                imageUrl = "pizza_pepperoni.png"
            ),
        )

    private val fishRecipes: List<RecipeDto> =
        listOf(
            RecipeDto(
                id = 0,
                title = "Лосось в лимонно-чесночном соусе",
                ingredients = listOf(
                    IngredientDto("4", "шт", "стейки лосося (по 150-200г)"),
                    IngredientDto("2", "шт", "лимона"),
                    IngredientDto("4", "зубч", "чеснока"),
                    IngredientDto("3", "ст. л.", "оливкового масла"),
                    IngredientDto("несколько", "веточек", "тимьяна или розмарина"),
                    IngredientDto("по вкусу", "", "соль и перец")
                ),
                method = listOf(
                    "Смешайте сок одного лимона, измельченный чеснок, оливковое масло, соль и перец.",
                    "Замаринуйте стейки лосося в соусе на 20-30 минут.",
                    "Разогрейте сковороду, обжарьте лосось с каждой стороны по 4-5 минут.",
                    "Добавьте веточки тимьяна в конце жарки для аромата.",
                    "Подавайте с дольками лимона и свежими овощами."
                ),
                imageUrl = "fish_salmon.png"
            ),
            RecipeDto(
                id = 1,
                title = "Запеченная треска с овощами",
                ingredients = listOf(
                    IngredientDto("4", "шт", "филе трески"),
                    IngredientDto("2", "шт", "кабачка"),
                    IngredientDto("2", "шт", "помидора"),
                    IngredientDto("1", "шт", "луковица"),
                    IngredientDto("2", "зубч", "чеснока"),
                    IngredientDto("3", "ст. л.", "оливкового масла"),
                    IngredientDto("по вкусу", "", "соль, перец, прованские травы")
                ),
                method = listOf(
                    "Разогрейте духовку до 200°C.",
                    "Нарежьте овощи крупными кусками, чеснок мелко.",
                    "Выложите овощи на противень, сбрызните маслом, посолите, поперчите.",
                    "Запекайте 15 минут, затем добавьте филе трески.",
                    "Посыпьте рыбу прованскими травами, запекайте еще 15-20 минут.",
                    "Подавайте горячим с лимоном."
                ),
                imageUrl = "fish_cod.png"
            ),
        )

    private val soupRecipes: List<RecipeDto> =
        listOf(
            RecipeDto(
                id = 0,
                title = "Томатный суп с базиликом",
                ingredients = listOf(
                    IngredientDto("1", "кг", "помидоров"),
                    IngredientDto("1", "шт", "луковица"),
                    IngredientDto("2", "зубч", "чеснока"),
                    IngredientDto("500", "мл", "овощного бульона"),
                    IngredientDto("2", "ст. л.", "оливкового масла"),
                    IngredientDto("1", "пучок", "базилика"),
                    IngredientDto("по вкусу", "", "соль, перец, сахар")
                ),
                method = listOf(
                    "Обжарьте мелко нарезанный лук и чеснок на оливковом масле.",
                    "Добавьте нарезанные помидоры, тушите 10 минут.",
                    "Влейте бульон, доведите до кипения и варите 15 минут.",
                    "Добавьте базилик, пробить суп блендером до однородности.",
                    "Посолите, поперчите, добавьте сахар по вкусу.",
                    "Подавайте с гренками и свежим базиликом."
                ),
                imageUrl = "soup_tomato.png"
            ),
            RecipeDto(
                id = 1,
                title = "Грибной суп-пюре",
                ingredients = listOf(
                    IngredientDto("500", "г", "шампиньонов"),
                    IngredientDto("1", "шт", "луковица"),
                    IngredientDto("2", "шт", "картофелины"),
                    IngredientDto("1", "л", "овощного бульона"),
                    IngredientDto("100", "мл", "сливок 20%"),
                    IngredientDto("2", "ст. л.", "сливочного масла"),
                    IngredientDto("по вкусу", "", "соль, перец, мускатный орех")
                ),
                method = listOf(
                    "Обжарьте лук и грибы на сливочном масле до золотистого цвета.",
                    "Добавьте нарезанный кубиками картофель, залейте бульоном.",
                    "Варите 20 минут до готовности картофеля.",
                    "Пробить суп блендером до однородности.",
                    "Влейте сливки, прогрейте, не доводя до кипения.",
                    "Посолите, поперчите, добавьте мускатный орех по вкусу.",
                    "Подавайте с сухариками и зеленью."
                ),
                imageUrl = "soup_mushroom.png"
            ),
        )

    private val saladRecipes: List<RecipeDto> =
        listOf(
            RecipeDto(
                id = 0,
                title = "Цезарь с курицей",
                ingredients = listOf(
                    IngredientDto("2", "шт", "куриных филе"),
                    IngredientDto("1", "шт", "салат романо"),
                    IngredientDto("100", "г", "пармезана"),
                    IngredientDto("150", "г", "белого хлеба для крутонов"),
                    IngredientDto("2", "зубч", "чеснока"),
                    IngredientDto("4", "ст. л.", "оливкового масла"),
                    IngredientDto("3", "ст. л.", "соуса цезарь")
                ),
                method = listOf(
                    "Куриное филе обжарьте на сковороде до готовности, нарежьте полосками.",
                    "Хлеб нарежьте кубиками, обжарьте с измельченным чесноком до золотистого цвета.",
                    "Салат романо вымойте, обсушите, порвите на крупные куски.",
                    "Сыр натрите на мелкой терке.",
                    "Смешайте салат с курицей, крутонами, соусом.",
                    "Посыпьте пармезаном перед подачей."
                ),
                imageUrl = "salad_caesar.png"
            ),
            RecipeDto(
                id = 1,
                title = "Греческий салат",
                ingredients = listOf(
                    IngredientDto("2", "шт", "огурца"),
                    IngredientDto("2", "шт", "помидора"),
                    IngredientDto("1", "шт", "сладкого перца"),
                    IngredientDto("1", "шт", "красного лука"),
                    IngredientDto("200", "г", "сыра фета"),
                    IngredientDto("100", "г", "оливок без косточек"),
                    IngredientDto("3", "ст. л.", "оливкового масла"),
                    IngredientDto("1", "ч. л.", "сушеного орегано")
                ),
                method = listOf(
                    "Нарежьте огурцы, помидоры и перец крупными кусками.",
                    "Лук нарежьте тонкими полукольцами.",
                    "Фету нарежьте кубиками.",
                    "Смешайте все овощи, добавьте оливки.",
                    "Заправьте оливковым маслом, посыпьте орегано.",
                    "Не солите — фета достаточно соленая."
                ),
                imageUrl = "salad_greek.png"
            ),
        )


    fun getCategories(): List<CategoryDto> {
        return categories
    }

    fun getRecipesByCategoryId(categoryId: Int): List<RecipeDto> {
        return when (categoryId) {
            0 -> burgerRecipes
            1 -> dessertRecipes
            2 -> pizzaRecipes
            3 -> fishRecipes
            4 -> soupRecipes
            5 -> saladRecipes
            else -> emptyList()
        }
    }

    fun getRecipeById(
        categoryId: Int,
        recipeId: Int,
    ): RecipeUiModel? {
        return getRecipesByCategoryId(categoryId).find { recipe ->
            recipeId == recipe.id
        }?.toUiModel()
    }

    fun getRecipeById(
        recipeId: Int,
    ): RecipeUiModel? {
        return listOf<List<RecipeDto>>(
            burgerRecipes,
            dessertRecipes,
            pizzaRecipes,
            fishRecipes,
            soupRecipes,
            saladRecipes
        ).flatten().find { it.id == recipeId }?.toUiModel()
    }
}