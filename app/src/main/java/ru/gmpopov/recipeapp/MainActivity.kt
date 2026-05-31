package ru.gmpopov.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.gmpopov.recipeapp.data.model.CategoryDto
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gmpopov.recipeapp.data.model.RecipeDto
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val okHttpClient = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(
            "MainActivityThread",
            "Метод onCreate выполняется на потоке: ${Thread.currentThread().name}"
        )

        thread {
            try {
                val categoryRequest: Request = Request.Builder()
                    .url("https://recipes.androidsprint.ru/api/category")
                    .build()

                val responseCategory = okHttpClient.newCall(categoryRequest).execute()
                val categoryBody = responseCategory.body?.string() ?: ""
                val deserializedCategory = Json.decodeFromString<List<CategoryDto>>(categoryBody)

                Log.d(
                    "api_response",
                    "Code: ${responseCategory.code}, Message: ${responseCategory.message}"
                )

                Log.d(
                    "NetworkThread",
                    "Выполняю запрос на потоке: ${Thread.currentThread().name}"
                )

                deserializedCategory.forEach { category ->
                    thread {
                        try {
                            val recipeRequest = Request.Builder()
                                .url("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
                                .build()

                            val responseRecipe = okHttpClient.newCall(recipeRequest).execute()
                            val recipesBody = responseRecipe.body?.string() ?: ""
                            val deserializedRecipe =
                                Json.decodeFromString<List<RecipeDto>>(recipesBody)

                            Log.d(
                                "response_recipes", "" +
                                        "Имя потока:${Thread.currentThread().name}, " +
                                        "Категория:${category.title}, " +
                                        "Всего рецептов:${deserializedRecipe.size}"
                            )
                        } catch (e: Exception) {
                            Log.e("network", "Ошибка: ${e.message}")
                        }
                    }
                }

                Log.d(
                    "Deserialized_body",
                    "Всего категорий: " +
                            "${deserializedCategory.size} ${deserializedCategory.map { it.title }}"
                )
            } catch (e: Exception) {
                Log.e("network", "Ошибка: ${e.message}")
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        intent.data?.let {
            deepLinkIntent = intent
        }
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let {
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}
